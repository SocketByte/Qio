package net.qio.lang.commands;

import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import net.qio.lang.QioInterpreter;
import net.qio.lang.exceptions.SyntaxException;
import net.qio.lang.memory.allocators.QioFunctionAllocator;
import net.qio.lang.memory.allocators.QioVariableAllocator;
import net.qio.lang.memory.allocators.QioWorkHolder;
import net.qio.lang.memory.work.IWork;
import net.qio.lang.memory.work.WorkLoop;
import net.qio.lang.utilities.TypeDetector;
import net.qio.lang.utilities.types.Keyword;
import net.qio.lang.utilities.types.Type;

import java.util.List;

public class LoopStartCommand extends Command {
    public LoopStartCommand() {
        super(Keyword.LOOP_START);
    }

    @Override
    public void execute(int tabs, String syntax) {
        Type type = TypeDetector.detect(syntax, true);
        int loops = 0;

        boolean var = false;
        boolean func = false;
        try {
            switch (type) {
                case VARIABLE:
                    var = true;
                    loops = Integer.valueOf(QioVariableAllocator.getRealValue(QioVariableAllocator.pull(syntax)).toString());
                    break;
                case STRING:
                    loops = Integer.valueOf(syntax);
                    break;
                case VOID:
                    try {
                        throw new SyntaxException("Loop count can not be void.");
                    } catch (SyntaxException e) {
                        e.printStackTrace();
                    }
                    break;
                case INTEGER:
                    loops = Integer.valueOf(syntax);
                    break;
                case FUNCTION:
                    func = true;
                    loops = Integer.valueOf(QioFunctionAllocator.getCallback(syntax).toString());
                    break;
            }
        } catch (NumberFormatException e) {
            try {
                if (var)
                    loops = Double.valueOf(QioVariableAllocator.getRealValue(QioVariableAllocator.pull(syntax)).toString()).intValue();
                else
                    loops = Double.valueOf(QioFunctionAllocator.getCallback(syntax).toString()).intValue();
            } catch (Exception ex) {
                try {
                    throw new SyntaxException("Loop count can not be other than integer type.");
                } catch (SyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        }
        QioWorkHolder.setLoopIndex(QioWorkHolder.getLoopIndex() + 1);

        try {
            QioWorkHolder.getLoopWorks()
                    .get(QioWorkHolder.getLoopIndex())
                    .getLoopContainer()
                    .setLoops(loops);
        } catch (IndexOutOfBoundsException e) {
            WorkLoop workLoop = new WorkLoop(QioInterpreter.getLoopContainer());
            QioWorkHolder.getLoopWorks().add(workLoop);
            workLoop.getLoopContainer().setLoops(loops);
            QioInterpreter.getStaticWork().push(workLoop);
        }
    }
}
