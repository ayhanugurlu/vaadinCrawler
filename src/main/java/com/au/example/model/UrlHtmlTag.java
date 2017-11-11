package com.au.example.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UrlHtmlTag {

	@Id
	@GeneratedValue
	private Long id;

	private String url;

	private String htmlTag;

	private String htmlTagAttribute;

	public UrlHtmlTag() {
	}

	public UrlHtmlTag(String url, String htmlTag,String htmlTagAttribute) {
		this.url = url;
		this.htmlTag = htmlTag;
		this.htmlTagAttribute = htmlTagAttribute;

	}

	public String getHtmlTagAttribute() {
		return htmlTagAttribute;
	}

	public void setHtmlTagAttribute(String htmlTagAttribute) {
		this.htmlTagAttribute = htmlTagAttribute;
	}

	public Long getId() {
		return id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHtmlTag() {
		return htmlTag;
	}

	public void setHtmlTag(String htmlTag) {
		this.htmlTag = htmlTag;
	}



}