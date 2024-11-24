-- Remover o trigger
DROP TRIGGER IF EXISTS before_weight_update ON users;

-- Alterar o tipo das colunas weight e height
ALTER TABLE users ALTER COLUMN weight TYPE REAL;
ALTER TABLE users ALTER COLUMN height TYPE REAL;

-- Recriar o trigger
CREATE TRIGGER before_weight_update
    BEFORE UPDATE OF weight ON users
    FOR EACH ROW
    WHEN (OLD.weight IS DISTINCT FROM NEW.weight)
EXECUTE FUNCTION log_weight_change();
