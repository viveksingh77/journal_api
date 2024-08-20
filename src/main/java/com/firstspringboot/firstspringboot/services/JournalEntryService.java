package com.firstspringboot.firstspringboot.services;

import com.firstspringboot.firstspringboot.entity.JournalEntry;
import com.firstspringboot.firstspringboot.entity.User;
import com.firstspringboot.firstspringboot.repository.JournalRepositoryEntry;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Component
public class JournalEntryService{

    @Autowired
    private JournalRepositoryEntry journalRepositoryEntry;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(JournalEntryService.class);

    @Transactional
    public void saveEntry(JournalEntry journalEntry , String userName){
        try {
            User user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDate.now());
            JournalEntry saved = journalRepositoryEntry.save(journalEntry);
            user.getJournalEntries().add(saved);
            //remove this
         //   user.setUserName(null);
            userService.saveUser(user);
        }catch (Exception e){
           logger.info("blbalblablalb");
            throw new RuntimeException("An error Occurred while saving the journal entry.", e);
        }

    }
    public void saveEntry(JournalEntry journalEntry){
        journalRepositoryEntry.save(journalEntry);
    }

    public List<JournalEntry> getAllEntry(){
        return journalRepositoryEntry.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalRepositoryEntry.findById(id);
    }

    @Transactional
    public  boolean deleteById(ObjectId id, String userName){
        boolean removed =false;
        try {
            User user = userService.findByUserName(userName);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (removed){
                userService.saveUser(user);
                journalRepositoryEntry.deleteById(id);
            }
        }catch (Exception e){
            System.out.println(e);
            throw  new RuntimeException("An error Occurred while deleting the entry.");
        }
        return removed;
    }

}
