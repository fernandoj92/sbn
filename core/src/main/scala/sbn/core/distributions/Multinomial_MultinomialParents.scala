package sbn.core.distributions

import sbn.core.variables.{Assignments, MultinomialType, Variable}

/**
  * This class defines the conditional distribution of a variable of [[MultinomialType]] whose parents are all also of [[MultinomialType]].
  * This distribution is composed of several [[Multinomial]] distributions, each one of them related to an Assignment of
  * the multinomial parents, resulting in a matrix of parameters.
  *
  * For example, if the variable (V) has 3 states (s0, s1, s2) and 1 parent (P) with 2 states (p0, p1), we would have the resulting
  * matrix (the Conditional Probability Table), where each row can be considered a [[Multinomial]] distribution
  * (in this example parameter values are random):
  *
  * <table>
  *   <tr>
  *     <th></th>
  *     <th>s0</th>
  *     <th>s1</th>
  *     <th>s2</th>
  *   </tr>
  *   <tr>
  *     <td>P = p0</td>
  *     <td>0.27</td>
  *     <td>0.43</td>
  *     <td>0.30</td>
  *   </tr>
  *   <tr>
  *     <td>P = p1</td>
  *     <td>0.05</td>
  *     <td>0.35</td>
  *     <td>0.60</td>
  *   </tr>
  * </table>
  *
  * @param variable the main variable of the distribution.
  * @param multinomialParents the parents of the variable.
  * @param parameterizedConditionalDistributions the resulting multinomial distributions of the variable.
  */
case class Multinomial_MultinomialParents(variable: Variable,
                                          multinomialParents: Set[Variable],
                                          parameterizedConditionalDistributions: Map[Assignments, Multinomial]) extends BaseDistribution_MultinomialParents(variable, multinomialParents, parameterizedConditionalDistributions) {

  /** @inheritdoc */
  override def label: String = "Multinomial | Multinomial"

}

/** The factory containing specific methods for creating [[Multinomial_MultinomialParents]] distribution objects */
object Multinomial_MultinomialParents {

  /**
    * Factory method that creates a [[Multinomial_MultinomialParents]] distribution with random parameters.
    *
    * @param variable the main variable of the distribution.
    * @param multinomialParents the conditioning variables
    * @throws IllegalArgumentException if the variable is not [[MultinomialType]] or
    *                                  if parents are not [[MultinomialType]].
    * @return a new [[Multinomial_MultinomialParents]] distribution with random parameters.
    */
  @throws[IllegalArgumentException]
  def apply(variable: Variable, multinomialParents: Set[Variable]): Multinomial_MultinomialParents ={
    require(variable.distributionType.isInstanceOf[MultinomialType], "Variable must be of multinomial type")
    require(!multinomialParents.exists(!_.distributionType.isInstanceOf[MultinomialType]), "Parents must be of multinomial type")

    val parametrizedMultinomialDistributions = BaseDistribution_MultinomialParents.generateAssignmentCombinations(multinomialParents)
      // .view makes it much faster because it avoids creating intermediate results.
      .view.map(assignments => assignments -> Multinomial(variable)).toMap

    Multinomial_MultinomialParents(variable, multinomialParents, parametrizedMultinomialDistributions)
  }

}