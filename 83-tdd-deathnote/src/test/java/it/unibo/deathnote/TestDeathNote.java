package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.function.Executable;

import static java.lang.Thread.sleep;

//import java.lang.reflect.Executable;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;

class TestDeathNote {

    private DeathNote deathNote;
    private String name = "Parapillo";
    private String name2 = "Salina";
    private String deathCause = "karting accident";
    private String details = "ran for too long";

    @BeforeEach
    public void setUp(){
      this.deathNote = new DeathNoteImpl();
    }

    @Test
    public void testRules(){
        DeathNote deathNote = new DeathNoteImpl();

        //check rule number 0
        assertThrows(IllegalArgumentException.class, new Executable (){
            @Override
            public void execute() throws Throwable {
                // TODO Auto-generated method stub
                deathNote.getRule(0);
            }
        });

        //check negative rules
        assertThrows(IllegalArgumentException.class, new Executable (){
            @Override
            public void execute() throws Throwable {
                // TODO Auto-generated method stub
                deathNote.getRule(-1);
            }
        });
    }

    @Test
    public void TestEMptyRules(){
        int nRules = deathNote.RULES.size();
        for(int i=1; i<=nRules; i++){
            assertNotNull(deathNote.getRule(i));
            assertFalse(deathNote.getRule(i).isEmpty());
        }
    }

    @Test
    public void TestWrite(){
        assertFalse(deathNote.isNameWritten(name));
        deathNote.writeName(name);
        assertTrue(deathNote.isNameWritten(name));
        assertFalse(deathNote.isNameWritten(name2));
        assertFalse(deathNote.isNameWritten(""));
    }

    @Test
    public void TestCauseOfDeath() throws InterruptedException{
        assertThrows(IllegalStateException.class, new Executable (){
            @Override
            public void execute() throws Throwable {
                // TODO Auto-generated method stub
                deathNote.writeDeathCause(deathCause);
            }
        });
        deathNote.writeName(name);
        assertEquals(deathNote.getDeathCause(name), "heart attack");
        deathNote.writeName(name2);
        assertTrue(deathNote.writeDeathCause(deathCause));
        assertEquals(deathCause, deathNote.getDeathCause(name2));
        sleep(100);
        assertFalse(deathNote.writeDeathCause("another death cause"));
        assertEquals(deathNote.getDeathCause(name2), deathCause);
    }

    @Test
    public void TestDetails() throws InterruptedException{
        assertThrows(IllegalStateException.class, new Executable (){
            @Override
            public void execute() throws Throwable {
                // TODO Auto-generated method stub
                deathNote.writeDetails(details);
            }
        });
        deathNote.writeName(name);
        assertTrue(deathNote.getDeathDetails(name).isEmpty());
        assertTrue(deathNote.writeDetails(details));
        assertEquals(deathNote.getDeathDetails(name), details);
        deathNote.writeName(name2);
        sleep(6400);
        deathNote.writeDetails("another detail");
        assertNotEquals(deathNote.getDeathDetails(name2), "another detail");

    }
}
