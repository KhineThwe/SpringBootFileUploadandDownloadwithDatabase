package com.jp.documentmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jp.documentmanager.entity.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long>{
     @Query("SELECT new Document(d.id,d.name,d.size) FROM Document d ORDER BY d.uploadTime DESC")
     List<Document> findAll();
}
