-- Alterar tabela training_sheets para training_days
ALTER TABLE training_sheets
    RENAME TO training_days;

-- Adicionar colunas à tabela training_days
ALTER TABLE training_days
    ADD COLUMN description TEXT;

-- Criar tabela training_day_exercises
CREATE TABLE training_day_exercises (
    training_day_id BIGINT REFERENCES training_days (id) ON DELETE CASCADE,
    exercise_id BIGINT REFERENCES exercises (id) ON DELETE CASCADE,
    default_series INT NOT NULL, -- Séries sugeridas
    default_repetitions INT NOT NULL, -- Repetições sugeridas
    default_weight NUMERIC(5, 2), -- Peso sugerido
    default_rest_interval INT, -- Descanso sugerido (em segundos)
    PRIMARY KEY (training_day_id, exercise_id)
);

-- Criar tabela training_programs
CREATE TABLE training_programs (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name TEXT NOT NULL, -- Nome da ficha de treino
    description TEXT, -- Descrição geral da ficha
    creator_id BIGINT REFERENCES users (id), -- Criador da ficha
    creation_date DATE NOT NULL DEFAULT CURRENT_DATE
);

-- Criar tabela training_program_days
CREATE TABLE training_program_days (
    training_program_id BIGINT REFERENCES training_programs (id) ON DELETE CASCADE,
    training_day_id BIGINT REFERENCES training_days (id) ON DELETE CASCADE,
    day_order INT NOT NULL, -- Ordem do dia dentro do programa
    PRIMARY KEY (training_program_id, training_day_id)
);

-- Adicionar colunas à tabela exercise_execution
ALTER TABLE exercise_execution
    ADD COLUMN series_number INT, -- Número da série
    ADD COLUMN completed BOOLEAN DEFAULT FALSE, -- Indica se a série foi concluída
    ADD COLUMN actual_rest_interval INT, -- Descanso real utilizado pelo aluno (em segundos)
    ADD COLUMN notes TEXT; -- Notas sobre a execução

-- Criar tabela series_execution
CREATE TABLE series_execution (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    exercise_execution_id BIGINT REFERENCES exercise_execution (id) ON DELETE CASCADE,
    series_number INT NOT NULL, -- Número da série
    actual_repetitions INT, -- Repetições realizadas
    actual_weight NUMERIC(5, 2), -- Peso utilizado
    actual_rest_interval INT, -- Descanso real utilizado
    completed BOOLEAN DEFAULT FALSE, -- Se a série foi completada
    notes TEXT -- Notas opcionais
);

-- Alterar tabela exercises para incluir categoria
ALTER TABLE exercises
    ADD COLUMN category TEXT; -- Exemplo: "Peito", "Costas", "Cardio"

-- Renomear tabela auto_trainer_training_sheets para auto_trainer_training_programs
ALTER TABLE auto_trainer_training_sheets
    RENAME TO auto_trainer_training_programs;

-- Alterar coluna training_sheet_id para training_program_id em auto_trainer_training_programs
ALTER TABLE auto_trainer_training_programs
    DROP COLUMN training_sheet_id,
    ADD COLUMN training_program_id BIGINT REFERENCES training_programs (id) ON DELETE CASCADE;

-- Atualizar a coluna is_created_by_self para manter o padrão FALSE
ALTER TABLE auto_trainer_training_programs
    ALTER COLUMN is_created_by_self SET DEFAULT FALSE;

-- Adicionar coluna profile_picture_url na tabela users
ALTER TABLE users
    ADD COLUMN profile_picture_url TEXT;

-- Adicionar coluna installment_count na tabela payment_history
ALTER TABLE payment_history
    ADD COLUMN installment_count INT DEFAULT 1; -- Número de parcelas (1 para pagamento único)