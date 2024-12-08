-- Remover a constraint de user_type, caso exista
ALTER TABLE users DROP CONSTRAINT IF EXISTS user_type_check;

-- Adicionar a nova constraint para user_type com valores em letras maiúsculas
ALTER TABLE users
    ADD CONSTRAINT user_type_check
        CHECK (user_type IN ('STUDENT', 'AUTO_TRAINER', 'PERSONAL_TRAINER'));

-- Remover a constraint de gender, caso exista
ALTER TABLE users DROP CONSTRAINT IF EXISTS user_gender_check;

-- Adicionar a nova constraint para gender com valores em letras maiúsculas
ALTER TABLE users
    ADD CONSTRAINT user_gender_check
        CHECK (gender IN ('MALE', 'FEMALE', 'OTHER'));