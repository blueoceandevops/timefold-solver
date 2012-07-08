/*
 * Copyright 2012 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.planner.core.heuristic.selector.move.generic;

import java.util.Iterator;

import org.drools.planner.core.heuristic.selector.SelectorTestUtils;
import org.drools.planner.core.heuristic.selector.entity.EntitySelector;
import org.drools.planner.core.heuristic.selector.value.ValueSelector;
import org.drools.planner.core.move.Move;
import org.drools.planner.core.move.generic.GenericChangeMove;
import org.drools.planner.core.phase.AbstractSolverPhaseScope;
import org.drools.planner.core.phase.step.AbstractStepScope;
import org.drools.planner.core.solver.DefaultSolverScope;
import org.drools.planner.core.testdata.domain.TestdataEntity;
import org.drools.planner.core.testdata.domain.TestdataValue;
import org.junit.Test;
import org.mockito.Matchers;

import static org.drools.planner.core.testdata.util.PlannerAssert.*;
import static org.mockito.Mockito.*;

public class ChangeMoveSelectorTest {

    @Test
    public void nonrandom() {
        EntitySelector entitySelector = SelectorTestUtils.mockEntitySelector(TestdataEntity.class,
                new TestdataEntity("a"), new TestdataEntity("b"), new TestdataEntity("c"), new TestdataEntity("d"));
        ValueSelector valueSelector = SelectorTestUtils.mockValueSelector(TestdataEntity.class, "value",
                new TestdataValue("1"), new TestdataValue("2"), new TestdataValue("3"));

        ChangeMoveSelector moveSelector = new ChangeMoveSelector(entitySelector, valueSelector, false);

        DefaultSolverScope solverScope = mock(DefaultSolverScope.class);
        moveSelector.solvingStarted(solverScope);

        AbstractSolverPhaseScope phaseScopeA = mock(AbstractSolverPhaseScope.class);
        when(phaseScopeA.getSolverScope()).thenReturn(solverScope);
        moveSelector.phaseStarted(phaseScopeA);

        AbstractStepScope stepScopeA1 = mock(AbstractStepScope.class);
        when(stepScopeA1.getSolverPhaseScope()).thenReturn(phaseScopeA);
        moveSelector.stepStarted(stepScopeA1);
        runAssertsNonrandom(moveSelector);
        moveSelector.stepEnded(stepScopeA1);

        AbstractStepScope stepScopeA2 = mock(AbstractStepScope.class);
        when(stepScopeA2.getSolverPhaseScope()).thenReturn(phaseScopeA);
        moveSelector.stepStarted(stepScopeA2);
        runAssertsNonrandom(moveSelector);
        moveSelector.stepEnded(stepScopeA2);

        moveSelector.phaseEnded(phaseScopeA);

        AbstractSolverPhaseScope phaseScopeB = mock(AbstractSolverPhaseScope.class);
        when(phaseScopeB.getSolverScope()).thenReturn(solverScope);
        moveSelector.phaseStarted(phaseScopeB);

        AbstractStepScope stepScopeB1 = mock(AbstractStepScope.class);
        when(stepScopeB1.getSolverPhaseScope()).thenReturn(phaseScopeB);
        moveSelector.stepStarted(stepScopeB1);
        runAssertsNonrandom(moveSelector);
        moveSelector.stepEnded(stepScopeB1);

        AbstractStepScope stepScopeB2 = mock(AbstractStepScope.class);
        when(stepScopeB2.getSolverPhaseScope()).thenReturn(phaseScopeB);
        moveSelector.stepStarted(stepScopeB2);
        runAssertsNonrandom(moveSelector);
        moveSelector.stepEnded(stepScopeB2);

        AbstractStepScope stepScopeB3 = mock(AbstractStepScope.class);
        when(stepScopeB3.getSolverPhaseScope()).thenReturn(phaseScopeB);
        moveSelector.stepStarted(stepScopeB3);
        runAssertsNonrandom(moveSelector);
        moveSelector.stepEnded(stepScopeB3);

        moveSelector.phaseEnded(phaseScopeB);

        moveSelector.solvingEnded(solverScope);

        verify(entitySelector, times(1)).solvingStarted(solverScope);
        verify(entitySelector, times(2)).phaseStarted(Matchers.<AbstractSolverPhaseScope>any());
        verify(entitySelector, times(5)).stepStarted(Matchers.<AbstractStepScope>any());
        verify(entitySelector, times(5)).stepEnded(Matchers.<AbstractStepScope>any());
        verify(entitySelector, times(2)).phaseEnded(Matchers.<AbstractSolverPhaseScope>any());
        verify(entitySelector, times(1)).solvingEnded(solverScope);
    }

    private void runAssertsNonrandom(ChangeMoveSelector moveSelector) {
        Iterator<Move> iterator = moveSelector.iterator();
        assertNotNull(iterator);
        assertNextChangeMove(iterator, "a", "1");
        assertNextChangeMove(iterator, "a", "2");
        assertNextChangeMove(iterator, "a", "3");
        assertNextChangeMove(iterator, "b", "1");
        assertNextChangeMove(iterator, "b", "2");
        assertNextChangeMove(iterator, "b", "3");
        assertNextChangeMove(iterator, "c", "1");
        assertNextChangeMove(iterator, "c", "2");
        assertNextChangeMove(iterator, "c", "3");
        assertNextChangeMove(iterator, "d", "1");
        assertNextChangeMove(iterator, "d", "2");
        assertNextChangeMove(iterator, "d", "3");
        assertFalse(iterator.hasNext());
        assertEquals(false, moveSelector.isContinuous());
        assertEquals(false, moveSelector.isNeverEnding());
        assertEquals(12L, moveSelector.getSize());
    }

    @Test
    public void emptyEntitySelectorNonrandom() {
        EntitySelector entitySelector = SelectorTestUtils.mockEntitySelector(TestdataEntity.class);
        ValueSelector valueSelector = SelectorTestUtils.mockValueSelector(TestdataEntity.class, "value",
                new TestdataValue("1"), new TestdataValue("2"), new TestdataValue("3"));

        ChangeMoveSelector moveSelector = new ChangeMoveSelector(entitySelector, valueSelector, false);

        DefaultSolverScope solverScope = mock(DefaultSolverScope.class);
        moveSelector.solvingStarted(solverScope);

        AbstractSolverPhaseScope phaseScopeA = mock(AbstractSolverPhaseScope.class);
        when(phaseScopeA.getSolverScope()).thenReturn(solverScope);
        moveSelector.phaseStarted(phaseScopeA);

        AbstractStepScope stepScopeA1 = mock(AbstractStepScope.class);
        when(stepScopeA1.getSolverPhaseScope()).thenReturn(phaseScopeA);
        moveSelector.stepStarted(stepScopeA1);
        runAssertsEmptyNonrandom(moveSelector);
        moveSelector.stepEnded(stepScopeA1);

        AbstractStepScope stepScopeA2 = mock(AbstractStepScope.class);
        when(stepScopeA2.getSolverPhaseScope()).thenReturn(phaseScopeA);
        moveSelector.stepStarted(stepScopeA2);
        runAssertsEmptyNonrandom(moveSelector);
        moveSelector.stepEnded(stepScopeA2);

        moveSelector.phaseEnded(phaseScopeA);

        AbstractSolverPhaseScope phaseScopeB = mock(AbstractSolverPhaseScope.class);
        when(phaseScopeB.getSolverScope()).thenReturn(solverScope);
        moveSelector.phaseStarted(phaseScopeB);

        AbstractStepScope stepScopeB1 = mock(AbstractStepScope.class);
        when(stepScopeB1.getSolverPhaseScope()).thenReturn(phaseScopeB);
        moveSelector.stepStarted(stepScopeB1);
        runAssertsEmptyNonrandom(moveSelector);
        moveSelector.stepEnded(stepScopeB1);

        AbstractStepScope stepScopeB2 = mock(AbstractStepScope.class);
        when(stepScopeB2.getSolverPhaseScope()).thenReturn(phaseScopeB);
        moveSelector.stepStarted(stepScopeB2);
        runAssertsEmptyNonrandom(moveSelector);
        moveSelector.stepEnded(stepScopeB2);

        AbstractStepScope stepScopeB3 = mock(AbstractStepScope.class);
        when(stepScopeB3.getSolverPhaseScope()).thenReturn(phaseScopeB);
        moveSelector.stepStarted(stepScopeB3);
        runAssertsEmptyNonrandom(moveSelector);
        moveSelector.stepEnded(stepScopeB3);

        moveSelector.phaseEnded(phaseScopeB);

        moveSelector.solvingEnded(solverScope);

        verify(entitySelector, times(1)).solvingStarted(solverScope);
        verify(entitySelector, times(2)).phaseStarted(Matchers.<AbstractSolverPhaseScope>any());
        verify(entitySelector, times(5)).stepStarted(Matchers.<AbstractStepScope>any());
        verify(entitySelector, times(5)).stepEnded(Matchers.<AbstractStepScope>any());
        verify(entitySelector, times(2)).phaseEnded(Matchers.<AbstractSolverPhaseScope>any());
        verify(entitySelector, times(1)).solvingEnded(solverScope);
    }

    @Test
    public void emptyValueSelectorNonrandom() {
        EntitySelector entitySelector = SelectorTestUtils.mockEntitySelector(TestdataEntity.class,
                new TestdataEntity("a"), new TestdataEntity("b"), new TestdataEntity("c"), new TestdataEntity("d"));
        ValueSelector valueSelector = SelectorTestUtils.mockValueSelector(TestdataEntity.class, "value");

        ChangeMoveSelector moveSelector = new ChangeMoveSelector(entitySelector, valueSelector, false);

        DefaultSolverScope solverScope = mock(DefaultSolverScope.class);
        moveSelector.solvingStarted(solverScope);

        AbstractSolverPhaseScope phaseScopeA = mock(AbstractSolverPhaseScope.class);
        when(phaseScopeA.getSolverScope()).thenReturn(solverScope);
        moveSelector.phaseStarted(phaseScopeA);

        AbstractStepScope stepScopeA1 = mock(AbstractStepScope.class);
        when(stepScopeA1.getSolverPhaseScope()).thenReturn(phaseScopeA);
        moveSelector.stepStarted(stepScopeA1);
        runAssertsEmptyNonrandom(moveSelector);
        moveSelector.stepEnded(stepScopeA1);

        AbstractStepScope stepScopeA2 = mock(AbstractStepScope.class);
        when(stepScopeA2.getSolverPhaseScope()).thenReturn(phaseScopeA);
        moveSelector.stepStarted(stepScopeA2);
        runAssertsEmptyNonrandom(moveSelector);
        moveSelector.stepEnded(stepScopeA2);

        moveSelector.phaseEnded(phaseScopeA);

        AbstractSolverPhaseScope phaseScopeB = mock(AbstractSolverPhaseScope.class);
        when(phaseScopeB.getSolverScope()).thenReturn(solverScope);
        moveSelector.phaseStarted(phaseScopeB);

        AbstractStepScope stepScopeB1 = mock(AbstractStepScope.class);
        when(stepScopeB1.getSolverPhaseScope()).thenReturn(phaseScopeB);
        moveSelector.stepStarted(stepScopeB1);
        runAssertsEmptyNonrandom(moveSelector);
        moveSelector.stepEnded(stepScopeB1);

        AbstractStepScope stepScopeB2 = mock(AbstractStepScope.class);
        when(stepScopeB2.getSolverPhaseScope()).thenReturn(phaseScopeB);
        moveSelector.stepStarted(stepScopeB2);
        runAssertsEmptyNonrandom(moveSelector);
        moveSelector.stepEnded(stepScopeB2);

        AbstractStepScope stepScopeB3 = mock(AbstractStepScope.class);
        when(stepScopeB3.getSolverPhaseScope()).thenReturn(phaseScopeB);
        moveSelector.stepStarted(stepScopeB3);
        runAssertsEmptyNonrandom(moveSelector);
        moveSelector.stepEnded(stepScopeB3);

        moveSelector.phaseEnded(phaseScopeB);

        moveSelector.solvingEnded(solverScope);

        verify(entitySelector, times(1)).solvingStarted(solverScope);
        verify(entitySelector, times(2)).phaseStarted(Matchers.<AbstractSolverPhaseScope>any());
        verify(entitySelector, times(5)).stepStarted(Matchers.<AbstractStepScope>any());
        verify(entitySelector, times(5)).stepEnded(Matchers.<AbstractStepScope>any());
        verify(entitySelector, times(2)).phaseEnded(Matchers.<AbstractSolverPhaseScope>any());
        verify(entitySelector, times(1)).solvingEnded(solverScope);
    }

    private void runAssertsEmptyNonrandom(ChangeMoveSelector moveSelector) {
        Iterator<Move> iterator = moveSelector.iterator();
        assertNotNull(iterator);
        assertFalse(iterator.hasNext());
        assertEquals(false, moveSelector.isContinuous());
        assertEquals(false, moveSelector.isNeverEnding());
        assertEquals(0L, moveSelector.getSize());
    }

    private void assertNextChangeMove(Iterator<Move> iterator, String entityCode, String toValueCode) {
        assertTrue(iterator.hasNext());
        GenericChangeMove move = (GenericChangeMove) iterator.next();
        assertCode(entityCode, move.getPlanningEntity());
        assertCode(toValueCode, move.getToPlanningValue());
    }

}
