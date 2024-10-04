CREATE TABLE users (
   id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
   name TEXT NOT NULL,
   email TEXT UNIQUE NOT NULL,
   password TEXT NOT NULL,
   active_plan BOOLEAN DEFAULT FALSE,
   user_type TEXT CHECK (user_type IN ('student', 'auto_trainer', 'gym', 'personal_trainer')) NOT NULL
);

CREATE TABLE subscriptions (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    user_id BIGINT REFERENCES users (id),
    plan TEXT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE,
    status TEXT CHECK (status IN ('active', 'expired', 'cancelled')) NOT NULL,
    price NUMERIC(10, 2) NOT NULL,
    payment_method TEXT
);

CREATE TABLE payment_history (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    subscription_id BIGINT REFERENCES subscriptions (id),
    payment_date DATE NOT NULL,
    amount_paid NUMERIC(10, 2) NOT NULL,
    payment_status TEXT CHECK (payment_status IN ('completed', 'pending', 'failed')) NOT NULL
);

CREATE TABLE training_sheets (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name TEXT NOT NULL,
    creation_date DATE NOT NULL,
    creator_id BIGINT REFERENCES users (id)
);

CREATE TABLE exercises (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name TEXT NOT NULL,
    description TEXT,
    media TEXT,
    current_weight NUMERIC(5, 2),
    rest_interval INT
);

CREATE TABLE training_sheet_exercises (
    training_sheet_id BIGINT REFERENCES training_sheets (id),
    exercise_id BIGINT REFERENCES exercises (id),
    PRIMARY KEY (training_sheet_id, exercise_id)
);

CREATE TABLE exercise_execution (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    student_id BIGINT REFERENCES users (id),
    exercise_id BIGINT REFERENCES exercises (id),
    weight_used NUMERIC(5, 2),
    repetitions INT,
    rest_time INT,
    execution_date DATE NOT NULL
);

CREATE TABLE student_training_sheets (
    student_id BIGINT REFERENCES users (id),
    training_sheet_id BIGINT REFERENCES training_sheets (id),
    start_date DATE NOT NULL,
    PRIMARY KEY (student_id, training_sheet_id)
);

ALTER TABLE training_sheet_exercises
ADD CONSTRAINT unique_exercise_per_sheet UNIQUE (training_sheet_id, exercise_id);

ALTER TABLE exercise_execution
ADD COLUMN execution_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

CREATE EXTENSION IF NOT EXISTS plpgsql;
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



