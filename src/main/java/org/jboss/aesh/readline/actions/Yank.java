/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.aesh.readline.actions;

import org.jboss.aesh.console.InputProcessor;
import org.jboss.aesh.readline.editing.EditMode;

/**
 * @author <a href="mailto:stale.pedersen@jboss.org">Ståle W. Pedersen</a>
 */
public class Yank extends ChangeAction {

    Yank() {
        super(EditMode.Status.YANK);
    }

    @Override
    public String name() {
        return "yank";
    }

    @Override
    public void apply(InputProcessor inputProcessor) {
        StringBuilder pasteBuffer = inputProcessor.getBuffer().getPasteManager().get(0);
        if(pasteBuffer != null) {

            addActionToUndoStack(inputProcessor);
            if(inputProcessor.getBuffer().getBuffer().getMultiCursor() <=
                    inputProcessor.getBuffer().getBuffer().getLine().length()) {
                inputProcessor.getBuffer().insertBufferLine(pasteBuffer.toString(),
                        inputProcessor.getBuffer().getBuffer().getMultiCursor());
                inputProcessor.getBuffer().drawLine();
            }
        }
    }
}
