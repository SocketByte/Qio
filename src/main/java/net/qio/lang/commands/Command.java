package net.qio.lang.commands;

import lombok.Data;
import net.qio.lang.utilities.types.Keyword;

@Data
public abstract class Command {

    private Keyword keyword;

    public Command(Keyword keyword) {
        this.keyword = keyword;
    }

    public abstract void execute(int tabs, String syntax);

}
