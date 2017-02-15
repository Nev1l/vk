package command;

import java.io.IOException;

/**
 * Created by Viktar_Kapachou on 12.02.2017.
 */
public interface ICommand {
    boolean execute() throws IOException;
}
