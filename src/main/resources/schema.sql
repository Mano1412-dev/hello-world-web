
IF OBJECT_ID('dbo.submitted_names', 'U') IS NULL
BEGIN
    CREATE TABLE dbo.submitted_names (
        id BIGINT IDENTITY(1,1) PRIMARY KEY,
        name NVARCHAR(100) NOT NULL,
        created_at DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME()
    );
END;
