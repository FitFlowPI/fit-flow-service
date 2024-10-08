-- Add specific columns for user attributes
ALTER TABLE users
ADD COLUMN gender TEXT CHECK (gender IN ('male', 'female', 'other')),
ADD COLUMN weight NUMERIC(5, 2),
ADD COLUMN height NUMERIC(5, 2);

-- Rename column in exercise_execution to generalize for all user types
ALTER TABLE exercise_execution
    RENAME COLUMN student_id TO user_id;

-- Add columns for additional payment details in payment_history
ALTER TABLE payment_history
ADD COLUMN transaction_id TEXT,
ADD COLUMN payment_method_details TEXT;

-- Create table to relate auto trainers with training sheets
CREATE TABLE auto_trainer_training_sheets (
    auto_trainer_id BIGINT REFERENCES users (id),
    training_sheet_id BIGINT REFERENCES training_sheets (id),
    is_created_by_self BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (auto_trainer_id, training_sheet_id)
);

-- Create table to relate personal trainers with students
CREATE TABLE personal_trainer_students (
    personal_trainer_id BIGINT REFERENCES users (id),
    student_id BIGINT REFERENCES users (id),
    PRIMARY KEY (personal_trainer_id, student_id)
);

-- Create table to log user weight history
CREATE TABLE user_weight_history (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    user_id BIGINT REFERENCES users (id),
    weight NUMERIC(5, 2),
    recorded_date DATE NOT NULL DEFAULT CURRENT_DATE
);

-- Create table to track subscription changes over time
CREATE TABLE subscription_history (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    subscription_id BIGINT REFERENCES subscriptions (id),
    change_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    old_status TEXT,
    new_status TEXT,
    old_plan TEXT,
    new_plan TEXT,
    notes TEXT
);

-- Create table to log payment attempts, including failures
CREATE TABLE payment_attempts (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    subscription_id BIGINT REFERENCES subscriptions (id),
    attempt_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    amount_attempted NUMERIC(10, 2),
    attempt_status TEXT CHECK (attempt_status IN ('completed', 'pending', 'failed')),
    failure_reason TEXT
);

-- Update function to manage active plan status
CREATE OR REPLACE FUNCTION update_active_plan()
    RETURNS TRIGGER AS $$
BEGIN
    IF NEW.status = 'active' THEN
        UPDATE users SET active_plan = TRUE WHERE id = NEW.user_id;
    ELSE
        UPDATE users SET active_plan = FALSE WHERE id = NEW.user_id;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Create function to log weight changes
CREATE OR REPLACE FUNCTION log_weight_change()
    RETURNS TRIGGER AS $$
BEGIN
    -- Insert previous weight into history table
    INSERT INTO user_weight_history (user_id, weight, recorded_date)
    VALUES (OLD.id, OLD.weight, CURRENT_DATE);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Create trigger to log weight changes before update
CREATE TRIGGER before_weight_update
BEFORE UPDATE OF weight ON users
FOR EACH ROW
WHEN (OLD.weight IS DISTINCT FROM NEW.weight)
EXECUTE FUNCTION log_weight_change();