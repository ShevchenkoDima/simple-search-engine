package conductor.cli;

import static org.apache.commons.cli.Option.UNLIMITED_VALUES;

import conductor.exception.InvalidOptionException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class CliOptions {

    public static Options getOptions() {
        Option getOption = new Option("get", "getDocument", true, "Get document by unique key.");
        getOption.setArgs(1);

        Option putOption = new Option("put", "putDocument", true,
                "Put document(list of words) into the search engine by key. " +
                        "First argument is a key. Any other arguments are words to add into document.");
        putOption.setArgs(UNLIMITED_VALUES);

        Option searchOption = new Option("search", "searchDocuments", true,
                "Search on a string of tokens to return keys of all documents that contain all tokens.");
        searchOption.setArgs(UNLIMITED_VALUES);

        Options options = new Options();
        options.addOption(getOption);
        options.addOption(putOption);
        options.addOption(searchOption);
        return options;
    }

    public static void validate(CommandLine cmd) throws InvalidOptionException {
        boolean get = cmd.hasOption("get");
        boolean put = cmd.hasOption("put");
        boolean search = cmd.hasOption("search");

        boolean isOnlyOneOptionSelected = (get ^ put ^ search) ^ (get && put && search);
        if (!isOnlyOneOptionSelected) {
            throw new InvalidOptionException("Please select exactly one option.");
        }
        if (put && cmd.getOptionValues("put").length < 2) {
            throw new InvalidOptionException("Option 'put' should have key and one or more word(s)");
        }
        if (search && cmd.getOptionValues("search").length < 1) {
            throw new InvalidOptionException("Option 'search' should have at least one search token.");
        }
    }

}