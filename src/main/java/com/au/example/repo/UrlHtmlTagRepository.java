package com.au.example.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.au.example.model.UrlHtmlTag;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;


@VaadinSessionScope
public interface UrlHtmlTagRepository extends JpaRepository<UrlHtmlTag, Long> {

		List<UrlHtmlTag>  findByUrlStartsWithIgnoreCase(String text);

}