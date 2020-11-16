package com.jvi.swat.ms.authorization.entity;

import com.jvi.swat.ms.authorization.model.Role;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
  @SequenceGenerator(name = "user_generator", sequenceName = "user_sequence", allocationSize = 1)
  private long id;

  @Column(unique = true)
  @NotBlank
  private String username;

  @Column
  @NotBlank
  private String encodedPassword;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "Role", joinColumns = @JoinColumn(name = "id"))
  @Enumerated(EnumType.STRING)
  private Set<Role> roles;

}

