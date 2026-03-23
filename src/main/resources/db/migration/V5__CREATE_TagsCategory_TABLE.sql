CREATE TABLE TagsCategory (
    Id TINYINT IDENTITY(1,1) PRIMARY KEY,
    Name NVARCHAR(50) NOT NULL UNIQUE
);
GO

INSERT INTO TagsCategory (Name) VALUES ('Private');
INSERT INTO TagsCategory (Name) VALUES ('Corporate');
GO