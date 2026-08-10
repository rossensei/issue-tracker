-- ============================================================
-- Issue Tracker - Initial Database Schema
-- Flyway Migration: V1
-- ============================================================

-- ============================================================
-- ENUM TYPES
-- ============================================================
CREATE TYPE project_member_role AS ENUM ('OWNER', 'ADMIN', 'MEMBER');

-- ============================================================
-- USERS
-- ============================================================

CREATE TABLE users (
    id UUID PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


-- ============================================================
-- PROJECTS
-- ============================================================

CREATE TABLE projects (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


-- ============================================================
-- PROJECT MEMBERS
-- ============================================================

CREATE TABLE project_members (
    id UUID PRIMARY KEY,
    project_id UUID NOT NULL,
    user_id UUID NOT NULL,
    role project_member_role NOT NULL DEFAULT 'MEMBER',
    joined_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_project_member_project
        FOREIGN KEY (project_id)
        REFERENCES projects(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_project_member_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT uq_project_member
        UNIQUE (project_id, user_id)
);


-- ============================================================
-- ISSUES
-- ============================================================

CREATE TABLE issues (
    id UUID PRIMARY KEY,
    project_id UUID NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(50) NOT NULL DEFAULT 'TODO',
    priority VARCHAR(50) NOT NULL DEFAULT 'MEDIUM',
    created_by UUID NOT NULL,
    assigned_to UUID,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_issue_project
        FOREIGN KEY (project_id)
        REFERENCES projects(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_issue_creator
        FOREIGN KEY (created_by)
        REFERENCES users(id),

    CONSTRAINT fk_issue_assignee
        FOREIGN KEY (assigned_to)
        REFERENCES users(id)
        ON DELETE SET NULL
);


-- ============================================================
-- COMMENTS
-- ============================================================

CREATE TABLE comments (
    id UUID PRIMARY KEY,
    issue_id UUID NOT NULL,
    user_id UUID NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_comment_issue
        FOREIGN KEY (issue_id)
        REFERENCES issues(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_comment_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
);


-- ============================================================
-- INDEXES
-- ============================================================

CREATE INDEX idx_project_members_project_id
    ON project_members(project_id);

CREATE INDEX idx_project_members_user_id
    ON project_members(user_id);

CREATE INDEX idx_issues_project_id
    ON issues(project_id);

CREATE INDEX idx_issues_created_by
    ON issues(created_by);

CREATE INDEX idx_issues_assigned_to
    ON issues(assigned_to);

CREATE INDEX idx_issues_status
    ON issues(status);

CREATE INDEX idx_comments_issue_id
    ON comments(issue_id);

CREATE INDEX idx_comments_user_id
    ON comments(user_id);
