package conductor.cli;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static conductor.cli.CliOptions.getOptions;
import static conductor.cli.CliOptions.validate;

import java.util.List;

import conductor.action.DocumentRequestAction;
import conductor.action.GetDocumentRequestAction;
import conductor.action.PutDocumentRequestAction;
import conductor.action.SearchDocumentRequestAction;
import conductor.exception.InvalidOptionException;
import conductor.service.DocumentHttpService;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class SimpleSearchEngineCli {

    public static void main(String[] args) throws Exception {
        CommandLine cmd = getCommandLine(args, getOptions());
        DocumentRequestAction action = getDocumentRequestAction(cmd);
        String result = action.execute(new DocumentHttpService());
        System.out.println(result);
    }

    private static CommandLine getCommandLine(String[] args, Options options) {
        try {
            CommandLine cmd = new DefaultParser().parse(options, args);
            validate(cmd);
            return cmd;
        } catch (InvalidOptionException | ParseException e) {
            System.out.println(e.getMessage());
            new HelpFormatter().printHelp("Please select one option to use simple search engine", getOptions());
            System.exit(1);
            return null;
        }
    }

    private static DocumentRequestAction getDocumentRequestAction(CommandLine cmd) {
        if (cmd.hasOption("get")) {
            return new GetDocumentRequestAction(cmd.getOptionValue("get"));
        }
        if (cmd.hasOption("put")) {
            List<String> words = stream(cmd.getOptionValues("put")).collect(toList());
            String key = words.remove(0);
            return new PutDocumentRequestAction(key, String.join(" ", words));
        }
        if (cmd.hasOption("search")) {
            return new SearchDocumentRequestAction(asList(cmd.getOptionValues("search")));
        }
        throw new IllegalArgumentException("Please select exactly one option.");
    }
}