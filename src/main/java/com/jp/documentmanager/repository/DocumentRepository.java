package com.jp.documentmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jp.documentmanager.entity.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long>{

}
