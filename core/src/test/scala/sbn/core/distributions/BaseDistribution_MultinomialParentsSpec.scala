package sbn.core.distributions

import sbn.core.CustomSpec
import sbn.core.variables.{Assignment, Assignments, Variable, VariableFactory}

/**
  * Created by fer on 23/11/16.
  */
class BaseDistribution_MultinomialParentsSpec extends CustomSpec{

  "BaseDistribution_MultinomialParents.generateAssignmentsCombinations" should "" in{

    Given("a multinomial variable (3 params) and a set of 3 multinomial parents with a number of 2, 2, 3 parameters respectively ")
    val parent_1 = VariableFactory.newMultinomialVariable("parent_1", 2)
    val parent_2 = VariableFactory.newMultinomialVariable("parent_2", 2)
    val parent_3 = VariableFactory.newMultinomialVariable("parent_3", 3)
    val variable = VariableFactory.newMultinomialVariable("variable", 3)
    val parents: Set[Variable] = Set(parent_1, parent_2, parent_3)

    When("calling generateAssingmentsCombinations(parents)")
    val combinations = BaseDistribution_MultinomialParents.generateAssignmentCombinations(parents)

    Then("the returned set of combinations should have size 2*2*3")
    assert(combinations.size == 2*2*3)

    And("combinations(8) should return [parent_1 = , parent_2 = , parent_3 = ]")
    assert(combinations(8) == Assignments(Set(Assignment(parent_1, 1), Assignment(parent_2, 0), Assignment(parent_3, 2))))
  }
}
