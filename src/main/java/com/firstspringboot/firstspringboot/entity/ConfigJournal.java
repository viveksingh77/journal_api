package com.firstspringboot.firstspringboot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("journal_config")
@Data
@NoArgsConstructor
public class ConfigJournal {
    private String key;
    private String value;
}
