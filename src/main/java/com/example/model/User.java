package com.example.model;

import com.example.views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.UserSummary.class)
    private Long id;

    @JsonView(Views.UserSummary.class)
    private String name;

    @Email
    @JsonView(Views.UserSummary.class)
    private String email;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonView(Views.UserDetails.class)
    private List<Order> orders;
}
