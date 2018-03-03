package net.qio.lang.commands;

import net.qio.lang.QioInterpreter;
import net.qio.lang.exceptions.SyntaxException;
import net.qio.lang.memory.allocators.QioFunctionAllocator;
import net.qio.lang.splitters.TypeSplitter;
import net.qio.lang.utilities.StringUtilities;
import net.qio.lang.utilities.types.Keyword;
import net.qio.lang.utilities.types.Type;

public class FuncStartCommand extends Command {

    public FuncStartCommand() {
        super(Keyword.FUNC_START);
    }

    @Override
    public void execute(int tabs, String syntax) {
        if (StringUtilities.checkTabs(tabs)) return;

        TypeSplitter splitter = null;
        try {
            splitter = new TypeSplitter(syntax);
        } catch (SyntaxException e) {
            e.printStackTrace();
        }
        String functionName = splitter.getFirstPart();
        String typeName = splitter.getSecondPart();

        QioInterpreter.getContainer().setName(functionName);

        Type type = Type.valueOf(typeName);
        if (type == Type.STRING) {
            QioInterpreter.getContainer().setType(String.class);
            QioFunctionAllocator.createDummyFunction(functionName);
        }
        else if (type == Type.INTEGER || typeName.equalsIgnoreCase("int")) {
            QioInterpreter.getContainer().setType(Integer.class);
            QioFunctionAllocator.createDummyFunction(functionName);
        }
        else if (type == Type.VOID) {
            QioInterpreter.getContainer().setType(null);
            QioFunctionAllocator.createDummyFunction(functionName);
        }
        else {
            try {
                throw new SyntaxException("Invalid type (?)");
            } catch (SyntaxException e) {
                e.printStackTrace();
            }
        }

        QioInterpreter.getContainer().setFunctionCall(true);
    }
}
