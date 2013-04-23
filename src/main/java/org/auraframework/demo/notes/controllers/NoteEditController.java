package org.auraframework.demo.notes.controllers;

import java.math.BigDecimal;

import org.auraframework.demo.notes.DataStore;
import org.auraframework.demo.notes.Note;
import org.auraframework.system.Annotations.AuraEnabled;
import org.auraframework.system.Annotations.Controller;
import org.auraframework.system.Annotations.Key;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

@Controller
public class NoteEditController {

    @AuraEnabled
    public static Note saveNote(@Key("id") Long id,
            @Key("title") String title,
            @Key("body") String body,
            @Key("sort") String sort,
            @Key("latitude") BigDecimal latitude,
            @Key("longitude") BigDecimal longitude) throws Exception {
        Dao<Note, Long> noteDao = DaoManager.createDao(DataStore.getInstance().getConnectionSource(), Note.class);
        Note note;

        if (id != null) {
            note = noteDao.queryForId(id);
        } else {
            note = new Note();
        }

        if (title == null) {
            title = "";
        }

        if (body == null) {
            body = "";
        }

        note.setTitle(title);
        note.setBody(body);
        if (latitude != null && longitude != null) {
            note.setLatitude(latitude.doubleValue());
            note.setLongitude(longitude.doubleValue());
        }

        if (id != null) {
            noteDao.update(note);
        } else {
            noteDao.create(note);
        }

        return note;
    }
}
