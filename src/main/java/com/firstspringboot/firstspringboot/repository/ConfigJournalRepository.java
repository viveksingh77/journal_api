package com.firstspringboot.firstspringboot.repository;

import com.firstspringboot.firstspringboot.entity.ConfigJournal;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalRepository extends MongoRepository<ConfigJournal , ObjectId> {
}
