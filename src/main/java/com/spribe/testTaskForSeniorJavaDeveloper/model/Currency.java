package com.spribe.testTaskForSeniorJavaDeveloper.model;

import com.spribe.testTaskForSeniorJavaDeveloper.dto.CurrencyDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "currency")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "code")
    private String code;
    @Column(name = "name")
    private String name;
    @Column(name ="used")
    private Boolean used;

    public CurrencyDTO toDTO() {
        return CurrencyDTO.builder()
                .code(this.code)
                .name(this.name)
                .build();
    }
}
