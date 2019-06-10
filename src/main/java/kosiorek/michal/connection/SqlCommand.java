package kosiorek.michal.connection;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SqlCommand {
    private List<String> commands;

    private SqlCommand(SqlCommandBuilder sqlCommandBuilder) {
        this.commands = sqlCommandBuilder.commands;
    }

    public static SqlCommandBuilder builder() {
        return new SqlCommandBuilder();
    }

    public String toSql() {
        if (commands.isEmpty()) {
            return "";
        }

        return new StringBuilder()
                .append(commands.get(0))
                .append(commands.stream().skip(1).collect(Collectors.joining(", ")))
                .append(" );")
                .toString();
    }

    public static class SqlCommandBuilder {
        private List<String> commands = new ArrayList<>();

        public SqlCommandBuilder table(String tableName) {
            if (!commands.stream().anyMatch(c -> c.startsWith("create"))) {
                commands.add(0, MessageFormat.format("create table if not exists {0} ( ", tableName));
            }

            return this;
        }

        public SqlCommandBuilder primaryKey(String columnName) {
            if (!commands.stream().anyMatch(c -> c.contains("primary"))) {
                commands.add(1, MessageFormat.format("{0} integer primary key autoincrement ", columnName));
            }
            return this;
        }

        public SqlCommandBuilder stringColumn(String columnName, int length, String options) {
            commands.add(1, MessageFormat.format("{0} varchar({1}) {2} ", columnName, length, options));
            return this;
        }

        public SqlCommandBuilder intColumn(String columnName, String options) {
            commands.add(1, MessageFormat.format("{0} integer {1} ", columnName, options));
            return this;
        }

        public SqlCommandBuilder decimalColumn(String columnName, int precision, int scale, String options) {
            commands.add(1, MessageFormat.format("{0} decimal({1}, {2}) {3} ", columnName, precision, scale, options));
            return this;
        }

        public SqlCommandBuilder column(String columnName, String type, String options) {
            commands.add(1, MessageFormat.format("{0} {1} {2} ", columnName, type, options));
            return this;
        }

        public SqlCommandBuilder foreignKey(String columnName, String tableName, String referenceColumn, String options) {
            commands.add(MessageFormat.format("foreign key({0}) references {1}({2}) {3}", columnName, tableName, referenceColumn, options));
            return this;
        }

        public SqlCommand build() {
            return new SqlCommand(this);
        }
    }
}
