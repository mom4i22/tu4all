package com.momchil.TU4ALL.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.momchil.TU4ALL.dbo.UserDBO;

import java.sql.Date;
import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {UserRepository.class})
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.momchil.TU4ALL.dbo"})
@DataJpaTest(properties = {"spring.main.allow-bean-definition-overriding=true"})
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;



    /**
     * Method under test: {@link UserRepository#findByAlias(String)}
     */
    @Test
    void testFindByAlias() {
        UserDBO userDBO = new UserDBO();
        userDBO.setAlias("Alias");
        userDBO.setCommentNotifications(1);
        userDBO.setCourses(new ArrayList<>());
        userDBO.setDateOfBirth(new Date(1L));
        userDBO.setEmail("jane.doe@example.org");
        userDBO.setFaculty("Faculty");
        userDBO.setFacultyNumber("42");
        userDBO.setLikeNotifications(1);
        userDBO.setName("Name");
        userDBO.setPassword("iloveyou");
        userDBO.setProfilePicture("Profile Picture");
        userDBO.setRole("Role");

        UserDBO userDBO2 = new UserDBO();
        userDBO2.setAlias("42");
        userDBO2.setCommentNotifications(-1);
        userDBO2.setCourses(new ArrayList<>());
        userDBO2.setDateOfBirth(new Date(1L));
        userDBO2.setEmail("john.smith@example.org");
        userDBO2.setFaculty("42");
        userDBO2.setFacultyNumber("Faculty Number");
        userDBO2.setLikeNotifications(-1);
        userDBO2.setName("42");
        userDBO2.setPassword("Password");
        userDBO2.setProfilePicture("42");
        userDBO2.setRole("42");
        userRepository.save(userDBO);
        userRepository.save(userDBO2);
        assertSame(userDBO, userRepository.findByAlias("Alias"));
    }

    /**
     * Method under test: {@link UserRepository#findByEmail(String)}
     */
    @Test
    void testFindByEmail() {
        UserDBO userDBO = new UserDBO();
        userDBO.setAlias("Alias");
        userDBO.setCommentNotifications(1);
        userDBO.setCourses(new ArrayList<>());
        userDBO.setDateOfBirth(new Date(1L));
        userDBO.setEmail("jane.doe@example.org");
        userDBO.setFaculty("Faculty");
        userDBO.setFacultyNumber("42");
        userDBO.setLikeNotifications(1);
        userDBO.setName("Name");
        userDBO.setPassword("iloveyou");
        userDBO.setProfilePicture("Profile Picture");
        userDBO.setRole("Role");

        UserDBO userDBO2 = new UserDBO();
        userDBO2.setAlias("42");
        userDBO2.setCommentNotifications(-1);
        userDBO2.setCourses(new ArrayList<>());
        userDBO2.setDateOfBirth(new Date(1L));
        userDBO2.setEmail("john.smith@example.org");
        userDBO2.setFaculty("42");
        userDBO2.setFacultyNumber("Faculty Number");
        userDBO2.setLikeNotifications(-1);
        userDBO2.setName("42");
        userDBO2.setPassword("Password");
        userDBO2.setProfilePicture("42");
        userDBO2.setRole("42");
        userRepository.save(userDBO);
        userRepository.save(userDBO2);
        assertSame(userDBO, userRepository.findByEmail("jane.doe@example.org"));
    }

    /**
     * Method under test: {@link UserRepository#findAllExceptActiveUser(long)}
     */
    @Test
    void testFindAllExceptActiveUser() {
        UserDBO userDBO = new UserDBO();
        userDBO.setAlias("Alias");
        userDBO.setCommentNotifications(1);
        userDBO.setCourses(new ArrayList<>());
        userDBO.setDateOfBirth(new Date(1L));
        userDBO.setEmail("jane.doe@example.org");
        userDBO.setFaculty("Faculty");
        userDBO.setFacultyNumber("42");
        userDBO.setLikeNotifications(1);
        userDBO.setName("Name");
        userDBO.setPassword("iloveyou");
        userDBO.setProfilePicture("Profile Picture");
        userDBO.setRole("Role");

        UserDBO userDBO2 = new UserDBO();
        userDBO2.setAlias("42");
        userDBO2.setCommentNotifications(-1);
        userDBO2.setCourses(new ArrayList<>());
        userDBO2.setDateOfBirth(new Date(1L));
        userDBO2.setEmail("john.smith@example.org");
        userDBO2.setFaculty("42");
        userDBO2.setFacultyNumber("Faculty Number");
        userDBO2.setLikeNotifications(-1);
        userDBO2.setName("42");
        userDBO2.setPassword("Password");
        userDBO2.setProfilePicture("42");
        userDBO2.setRole("42");
        userRepository.save(userDBO);
        userRepository.save(userDBO2);
        assertEquals(1, userRepository.findAllExceptActiveUser(1L).size());
    }

    /**
     * Method under test: {@link UserRepository#findAllByStudent(UserDBO)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testFindAllByStudent() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   org.springframework.dao.DataIntegrityViolationException: could not execute statement; SQL [n/a]; constraint ["PUBLIC.UK_22ORGON25A45JT87HVBMKNKS2_INDEX_4 ON PUBLIC.USERS(ALIAS NULLS FIRST) VALUES ( /* 1 */ 'Alias' )"; SQL statement:
        //   insert into users (user_id, alias, comment_notifications, date_of_birth, email, faculty, faculty_number, like_notifications, name, password, profile_picture, role) values (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) [23505-220]]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement
        //       at com.sun.proxy.$Proxy128.save(null)
        //   org.hibernate.exception.ConstraintViolationException: could not execute statement
        //       at org.hibernate.exception.internal.SQLExceptionTypeDelegate.convert(SQLExceptionTypeDelegate.java:59)
        //       at org.hibernate.exception.internal.StandardSQLExceptionConverter.convert(StandardSQLExceptionConverter.java:37)
        //       at org.hibernate.engine.jdbc.spi.SqlExceptionHelper.convert(SqlExceptionHelper.java:113)
        //       at org.hibernate.engine.jdbc.spi.SqlExceptionHelper.convert(SqlExceptionHelper.java:99)
        //       at org.hibernate.engine.jdbc.internal.ResultSetReturnImpl.executeUpdate(ResultSetReturnImpl.java:200)
        //       at org.hibernate.dialect.identity.GetGeneratedKeysDelegate.executeAndExtract(GetGeneratedKeysDelegate.java:58)
        //       at org.hibernate.id.insert.AbstractReturningDelegate.performInsert(AbstractReturningDelegate.java:43)
        //       at org.hibernate.persister.entity.AbstractEntityPersister.insert(AbstractEntityPersister.java:3279)
        //       at org.hibernate.persister.entity.AbstractEntityPersister.insert(AbstractEntityPersister.java:3914)
        //       at org.hibernate.action.internal.EntityIdentityInsertAction.execute(EntityIdentityInsertAction.java:84)
        //       at org.hibernate.engine.spi.ActionQueue.execute(ActionQueue.java:645)
        //       at org.hibernate.engine.spi.ActionQueue.addResolvedEntityInsertAction(ActionQueue.java:282)
        //       at org.hibernate.engine.spi.ActionQueue.addInsertAction(ActionQueue.java:263)
        //       at org.hibernate.engine.spi.ActionQueue.addAction(ActionQueue.java:317)
        //       at org.hibernate.event.internal.AbstractSaveEventListener.addInsertAction(AbstractSaveEventListener.java:329)
        //       at org.hibernate.event.internal.AbstractSaveEventListener.performSaveOrReplicate(AbstractSaveEventListener.java:286)
        //       at org.hibernate.event.internal.AbstractSaveEventListener.performSave(AbstractSaveEventListener.java:192)
        //       at org.hibernate.event.internal.AbstractSaveEventListener.saveWithGeneratedId(AbstractSaveEventListener.java:122)
        //       at org.hibernate.event.internal.DefaultPersistEventListener.entityIsTransient(DefaultPersistEventListener.java:185)
        //       at org.hibernate.event.internal.DefaultPersistEventListener.onPersist(DefaultPersistEventListener.java:128)
        //       at org.hibernate.event.internal.DefaultPersistEventListener.onPersist(DefaultPersistEventListener.java:55)
        //       at org.hibernate.event.service.internal.EventListenerGroupImpl.fireEventOnEachListener(EventListenerGroupImpl.java:107)
        //       at org.hibernate.internal.SessionImpl.firePersist(SessionImpl.java:756)
        //       at org.hibernate.internal.SessionImpl.persist(SessionImpl.java:742)
        //       at com.sun.proxy.$Proxy122.persist(null)
        //       at com.sun.proxy.$Proxy128.save(null)
        //   org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException: Unique index or primary key violation: "PUBLIC.UK_22ORGON25A45JT87HVBMKNKS2_INDEX_4 ON PUBLIC.USERS(ALIAS NULLS FIRST) VALUES ( /* 1 */ 'Alias' )"; SQL statement:
        //   insert into users (user_id, alias, comment_notifications, date_of_birth, email, faculty, faculty_number, like_notifications, name, password, profile_picture, role) values (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) [23505-220]
        //       at org.h2.message.DbException.getJdbcSQLException(DbException.java:520)
        //       at org.h2.message.DbException.getJdbcSQLException(DbException.java:489)
        //       at org.h2.message.DbException.get(DbException.java:223)
        //       at org.h2.message.DbException.get(DbException.java:199)
        //       at org.h2.index.Index.getDuplicateKeyException(Index.java:527)
        //       at org.h2.mvstore.db.MVSecondaryIndex.checkUnique(MVSecondaryIndex.java:223)
        //       at org.h2.mvstore.db.MVSecondaryIndex.add(MVSecondaryIndex.java:184)
        //       at org.h2.mvstore.db.MVTable.addRow(MVTable.java:519)
        //       at org.h2.command.dml.Insert.insertRows(Insert.java:174)
        //       at org.h2.command.dml.Insert.update(Insert.java:135)
        //       at org.h2.command.CommandContainer.executeUpdateWithGeneratedKeys(CommandContainer.java:242)
        //       at org.h2.command.CommandContainer.update(CommandContainer.java:163)
        //       at org.h2.command.Command.executeUpdate(Command.java:252)
        //       at org.h2.jdbc.JdbcPreparedStatement.executeUpdateInternal(JdbcPreparedStatement.java:209)
        //       at org.h2.jdbc.JdbcPreparedStatement.executeUpdate(JdbcPreparedStatement.java:169)
        //       at org.hibernate.engine.jdbc.internal.ResultSetReturnImpl.executeUpdate(ResultSetReturnImpl.java:197)
        //       at org.hibernate.dialect.identity.GetGeneratedKeysDelegate.executeAndExtract(GetGeneratedKeysDelegate.java:58)
        //       at org.hibernate.id.insert.AbstractReturningDelegate.performInsert(AbstractReturningDelegate.java:43)
        //       at org.hibernate.persister.entity.AbstractEntityPersister.insert(AbstractEntityPersister.java:3279)
        //       at org.hibernate.persister.entity.AbstractEntityPersister.insert(AbstractEntityPersister.java:3914)
        //       at org.hibernate.action.internal.EntityIdentityInsertAction.execute(EntityIdentityInsertAction.java:84)
        //       at org.hibernate.engine.spi.ActionQueue.execute(ActionQueue.java:645)
        //       at org.hibernate.engine.spi.ActionQueue.addResolvedEntityInsertAction(ActionQueue.java:282)
        //       at org.hibernate.engine.spi.ActionQueue.addInsertAction(ActionQueue.java:263)
        //       at org.hibernate.engine.spi.ActionQueue.addAction(ActionQueue.java:317)
        //       at org.hibernate.event.internal.AbstractSaveEventListener.addInsertAction(AbstractSaveEventListener.java:329)
        //       at org.hibernate.event.internal.AbstractSaveEventListener.performSaveOrReplicate(AbstractSaveEventListener.java:286)
        //       at org.hibernate.event.internal.AbstractSaveEventListener.performSave(AbstractSaveEventListener.java:192)
        //       at org.hibernate.event.internal.AbstractSaveEventListener.saveWithGeneratedId(AbstractSaveEventListener.java:122)
        //       at org.hibernate.event.internal.DefaultPersistEventListener.entityIsTransient(DefaultPersistEventListener.java:185)
        //       at org.hibernate.event.internal.DefaultPersistEventListener.onPersist(DefaultPersistEventListener.java:128)
        //       at org.hibernate.event.internal.DefaultPersistEventListener.onPersist(DefaultPersistEventListener.java:55)
        //       at org.hibernate.event.service.internal.EventListenerGroupImpl.fireEventOnEachListener(EventListenerGroupImpl.java:107)
        //       at org.hibernate.internal.SessionImpl.firePersist(SessionImpl.java:756)
        //       at org.hibernate.internal.SessionImpl.persist(SessionImpl.java:742)
        //       at com.sun.proxy.$Proxy122.persist(null)
        //       at com.sun.proxy.$Proxy128.save(null)
        //   See https://diff.blue/R013 to resolve this issue.

        UserDBO userDBO = new UserDBO();
        userDBO.setAlias("Alias");
        userDBO.setCommentNotifications(1);
        userDBO.setCourses(new ArrayList<>());
        userDBO.setDateOfBirth(new Date(1L));
        userDBO.setEmail("jane.doe@example.org");
        userDBO.setFaculty("Faculty");
        userDBO.setFacultyNumber("42");
        userDBO.setLikeNotifications(1);
        userDBO.setName("Name");
        userDBO.setPassword("iloveyou");
        userDBO.setProfilePicture("Profile Picture");
        userDBO.setRole("Role");
        userRepository.save(userDBO);

        UserDBO userDBO2 = new UserDBO();
        userDBO2.setAlias("42");
        userDBO2.setCommentNotifications(-1);
        userDBO2.setCourses(new ArrayList<>());
        userDBO2.setDateOfBirth(new Date(1L));
        userDBO2.setEmail("john.smith@example.org");
        userDBO2.setFaculty("42");
        userDBO2.setFacultyNumber("Faculty Number");
        userDBO2.setLikeNotifications(-1);
        userDBO2.setName("42");
        userDBO2.setPassword("Password");
        userDBO2.setProfilePicture("42");
        userDBO2.setRole("42");
        userRepository.save(userDBO2);

        UserDBO userDBO3 = new UserDBO();
        userDBO3.setAlias("Alias");
        userDBO3.setCommentNotifications(1);
        userDBO3.setCourses(new ArrayList<>());
        userDBO3.setDateOfBirth(new Date(1L));
        userDBO3.setEmail("jane.doe@example.org");
        userDBO3.setFaculty("Faculty");
        userDBO3.setFacultyNumber("42");
        userDBO3.setLikeNotifications(1);
        userDBO3.setName("Name");
        userDBO3.setPassword("iloveyou");
        userDBO3.setProfilePicture("Profile Picture");
        userDBO3.setRole("Role");
        userRepository.findAllByStudent(userRepository.save(userDBO3));
    }
}

