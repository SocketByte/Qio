package net.qio.lang.commands;

import net.qio.lang.QioInterpreter;
import net.qio.lang.exceptions.SyntaxException;
import net.qio.lang.memory.allocators.QioFunctionAllocator;
import net.qio.lang.memory.allocators.QioVariableHolder;
import net.qio.lang.memory.work.Work;
import net.qio.lang.utilities.StringUtilities;
import net.qio.lang.utilities.TypeDetector;
import net.qio.lang.utilities.types.Keyword;

public class FuncEndCommand extends Command {

    public FuncEndCommand() {
        super(Keyword.FUNC_END);
    }

    @Override
    public void execute(int tabs, String syntax) {
        if (StringUtilities.checkTabs(tabs)) return;

        Object callback = TypeDetector.detectObject(syntax);
        Class<?> clazz = QioInterpreter.getContainer().getType();

        if (syntax == null || syntax.isEmpty()) {
            callback = null;
        }
        else {
            if (callback == null || clazz == null) {
                try {
                    throw new SyntaxException("You can not return void.");
                } catch (SyntaxException e) {
                    e.printStackTrace();
                }
                return;
            }

            Class<?> callbackClazz = callback.getClass();
            if (!QioVariableHolder.getVariableList().contains(callback.toString())) {
                if (!(callback.getClass().isAssignableFrom(clazz))) {
                    try {
                        throw new SyntaxException("Callback (return) value is not assignable from " + clazz.getName() + ".");
                    } catch (SyntaxException e) {
                        e.printStackTrace();
                    }
                    return;
                }
            }
            else {
                callback = "var@" + callback;
            }
        }

        String name = QioInterpreter.getContainer().getName();
        Work work = QioInterpreter.getContainer().getCurrentWork();

        QioFunctionAllocator.setFunction(name, callback, work);
        QioInterpreter.getContainer().clear();
    }

}
