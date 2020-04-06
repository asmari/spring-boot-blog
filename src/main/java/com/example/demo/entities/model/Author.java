package com.example.demo.entities.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * Author
 */
@Entity
@Table(name = "author")
@Getter
@Setter
@ToString
public class Author implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    @Column(length = 45, nullable = false)
    @Size(min = 3, max = 45)
    @NotBlank
    private String first_name;

    @Column(length = 45)
    @Size(min = 3, max = 45)
    private String last_name;

    @Column(length = 45, nullable = false, unique = true)
    @Size(min = 3, max = 45)
    @NotBlank
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(length = 150, nullable = false)
    @NotBlank
    private String password;

    @Column(updatable = false)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss",timezone="GMT+7")
    @CreationTimestamp
    private Date created_at;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss",timezone="GMT+7")
    @UpdateTimestamp
    private Date updated_at;
}
