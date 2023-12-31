package com.jp.documentmanager.entity;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "documents")
public class Document {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "serial")
	private Long id;
	@Column(length = 512, nullable = false, unique = true)
	private String name;

	private long size;

	@Column(name = "upload_time")
	private Date uploadTime;

	private byte[] content;

	public Document() {
		super();
	}

	public Document(Long id, String name, long size) {
		super();
		this.id = id;
		this.name = name;
		this.size = size;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}
	
	@Transient
	public String getLogoImagePath() {
		if(name == null || id == null) return null;
		
		return "/brand-logos/" + id + "/" + name;
		
	}
	

}
