package sbn.core.statistics.distributions

import sbn.core.statistics.exponentialfamily.distributions.EF_Distribution
import sbn.core.variables.Assignments
import sbn.core.variables.model.{GaussianType, ModelVariable}

/**
  * Created by fer on 3/11/16.
  */
case class Gaussian_MultinomialParents(variable: ModelVariable,
                                       multinomialParents: Set[ModelVariable],
                                       parameterizedConditionalDistributions: Map[Assignments, Gaussian]) extends BaseDistribution_MultinomialParents(variable, multinomialParents, parameterizedConditionalDistributions){

  require(variable.distributionType.isInstanceOf[GaussianType], "Variable must be of gaussian type")

  /**
    * Returns the label of the distribution.
    *
    * @return The label of the distribution.
    */
  override def label: String = "Gaussian | Multinomial"

  /**
    * Returns the distribution in its Exponential Family form.
    *
    * @return the distribution in its Exponential Family form.
    */
  override def toEF_Distribution: EF_Distribution = ???
}

/** The factory containing specific methods for creating [[Gaussian_MultinomialParents]] distribution objects */
object Gaussian_MultinomialParents {

  /**
    * Factory method that creates a [[Gaussian_MultinomialParents]] distribution with random parameters.
    *
    * @param variable the main variable of the distribution.
    * @param multinomialParents the conditioning variables
    * @throws IllegalArgumentException if the variable is not [[GaussianType]] or
    *                                  if parents are not [[GaussianType]].
    * @return a new [[Gaussian_MultinomialParents]] distribution with random parameters.
    */
  @throws[IllegalArgumentException]
  def apply(variable: ModelVariable, multinomialParents: Set[ModelVariable]): Gaussian_MultinomialParents ={

    val parametrizedMultinomialDistributions = BaseDistribution_MultinomialParents.generateAssignmentCombinations(multinomialParents)
      // .view makes it much faster because it avoids creating intermediate results.
      .view.map(assignments => assignments -> Gaussian(variable)).toMap

    Gaussian_MultinomialParents(variable, multinomialParents, parametrizedMultinomialDistributions)
  }

}
