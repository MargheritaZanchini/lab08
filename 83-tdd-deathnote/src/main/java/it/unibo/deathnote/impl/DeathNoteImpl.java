package it.unibo.deathnote.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImpl implements DeathNote {

    static List<String> RULES = List.of(
        """
        The human whose name is written in this note shall die.
        """,
        """
        This note will not take effect unless the writer has the subject's face in mind when
        writing his/her name. This is to prevent people who share the same name from being
        affected.
        """,
        """
        After writing the cause of death, details of the death should be written in the next 6
        seconds and 40 milliseconds.
        """,
        """
        The human who touches the Death Note can recognize the image and voice of its original
        owner, a god of death, even if the human is not the owner of the note.
        """,
        """
        The person in possession of the Death Note is possessed by a god of death,
        its original owner, until they die.
        """,
        """
        Gods of death, the original owners of the Death Note, do not do, in principle,
        anything which will help or prevent the deaths in the note. A god of death has no
        obligation to completely explain how to use the note or rules which will apply to the
        human who owns it unless asked.
        """,
        """
        A god of death can extend their own life by putting a name on their own note, but
        humans cannot.
        """,
        """
        The human who becomes the owner of the Death Note can, in exchange of half his/her
        remaining life, get the eyeballs of the god of death which will enable him/her to see
        a human's name and remaining life span when looking through them.
        """,
        """
        The conditions for death will not be realized unless it is physically possible for
        that human or it is reasonably assumed to be carried out by that human.
        """,
        """
        One page taken from the Death Note, or even a fragment of the page, contains the full
        effects of the note.
        """,
        """
        The individuals who lose the ownership of the Death Note will also lose their memory
        of the usage of the Death Note. This does not mean that he will lose all the memory
        from the day he owned it to the day he loses possession, but means he will only lose
        the memory involving the Death Note.
        """,
        """
        The number of pages of the Death Note will never run out.
        """,
        """
        It is useless trying to erase names written in the Death Note with erasers or
        white-out.
        """
    );

    List<String> namesList = new ArrayList<>();
    List<String> causesOfDeathList = new ArrayList<>();
    List<String> detailsList = new ArrayList<>();

    long time = System.currentTimeMillis();

    public String getRule(int ruleNumber) {
        if(ruleNumber == 0){
            throw new IllegalArgumentException("no rule number 0");
        }else if(ruleNumber < 0){
            throw new IllegalArgumentException("no negative rules");
        }
        return RULES.get(ruleNumber-1);
    }

    public void writeName(String name) {
        if(name.equals(null)){
            throw new NullPointerException("the name is null");
        }
        namesList.add(name);
        causesOfDeathList.add("heart attack");
        detailsList.add("");
        time = System.currentTimeMillis();
    }

    @Override
    public boolean writeDeathCause(String cause) {
        time = System.currentTimeMillis() - time;
        if(time > 40){
            return false;
        }
        if(namesList.size() == 0){
            throw new IllegalStateException("no name");
        }
        if(cause.equals(null)){
            throw new IllegalStateException("cause is null");
        }
        causesOfDeathList.add(namesList.size()-1, cause);
        time = System.currentTimeMillis();
        return true;
    }

    @Override
    public boolean writeDetails(String details) {
        time = System.currentTimeMillis() - time;
        if(time > 6400){
            return false;
        }
        if(namesList.size() == 0){
            throw new IllegalStateException("no name");
        }
        if(details.equals(null)){
            throw new IllegalStateException("details are null");
        }
        detailsList.add(namesList.size()-1, details);
        return true;
    }

    @Override
    public String getDeathCause(String name) {
        for(int i=0; i<namesList.size(); i++){
            if(namesList.get(i).equals(name)){
                return causesOfDeathList.get(i);
            }
        }
        throw new IllegalArgumentException("no name found");
    }

    @Override
    public String getDeathDetails(String name) {
        for(int i=0; i<namesList.size(); i++){
            if(namesList.get(i).equals(name)){
                return detailsList.get(i);
            }
        }
        throw new IllegalArgumentException("no name found");
    }

    @Override
    public boolean isNameWritten(String name) {
        for(int i=0; i< namesList.size(); i++){
            if(namesList.get(i).equals(name)){
                return true;
            }
        }
        return false;
    }
    
}
