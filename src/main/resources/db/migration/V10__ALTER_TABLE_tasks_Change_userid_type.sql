ALTER TABLE task ADD user_id_new UNIQUEIDENTIFIER;
GO

UPDATE task SET user_id_new = NEWID() WHERE user_id_new IS NULL;
GO

ALTER TABLE task DROP CONSTRAINT FK_task_users; 
GO

ALTER TABLE task DROP COLUMN user_id;
GO

EXEC sp_rename 'task.user_id_new', 'user_id', 'COLUMN';
GO

ALTER TABLE task ALTER COLUMN user_id UNIQUEIDENTIFIER NOT NULL;
GO