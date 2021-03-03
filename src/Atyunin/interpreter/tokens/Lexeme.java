package Atyunin.interpreter.tokens;

public class Lexeme {

    protected LexType type;

    protected Lexeme(LexType type) {

        this.type = type;
    }

    public LexType get_type () {

        return type;
    }

    public Object get_value () {

        return null;
    }

    static public Lexeme create_lexeme(LexType type) {

        return new Lexeme(type);
    }

    static public Lexeme_str_val create_lexeme(LexType type, String value) {

        return new Lexeme_str_val(type, value);
    }

    static public Lexeme_num_val create_lexeme(LexType type, Integer value) {

        return new Lexeme_num_val(type, value);
    }
}

class Lexeme_str_val extends Lexeme {

    protected String value;

    protected Lexeme_str_val(LexType type, String value) {

        super(type);
        this.value = value;
    }

    public Object get_value () {

        return value;
    }
}

class Lexeme_num_val extends Lexeme {

    protected Integer value;

    protected Lexeme_num_val(LexType type, Integer value) {

        super(type);
        this.value = value;
    }

    public Object get_value () {

        return value;
    }
}