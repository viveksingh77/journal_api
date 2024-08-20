package com.firstspringboot.firstspringboot.repository;

import com.firstspringboot.firstspringboot.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalRepositoryEntry extends MongoRepository<JournalEntry, ObjectId> {

}
