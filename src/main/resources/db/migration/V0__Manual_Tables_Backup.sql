CREATE TABLE dbo.users (
    id bigint IDENTITY(1,1) NOT NULL PRIMARY KEY,
    password varchar(255) NOT NULL,
    role varchar(255) NULL,
    username varchar(255) NOT NULL UNIQUE
);
GO

CREATE TABLE dbo.task (
    id bigint IDENTITY(1,1) NOT NULL PRIMARY KEY,
    completed bit NOT NULL,
    description varchar(100) NULL,
    user_id bigint NULL
);
GO

ALTER TABLE dbo.task 
ADD CONSTRAINT FK_task_users FOREIGN KEY (user_id) REFERENCES dbo.users (id);
GO