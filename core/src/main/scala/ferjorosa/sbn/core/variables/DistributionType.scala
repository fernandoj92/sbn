package ferjorosa.sbn.core.variables

import ferjorosa.sbn.core.distributions._

/**
  * Represents the univariate distribution type of a variable.
  */
trait DistributionType{

  /**
    * Tests whether a given parent is compatible or not. In other words, it checks if the resulting conditional distribution
    * would be allowed by the library or not.
    * @param distributionType the parent's distribution type.
    * @return true if the parent is compatible, false otherwise.
    */
  def isParentCompatible(distributionType: DistributionType): Boolean

  /**
    * Creates a new [[UnivariateDistribution]] of the distribution type.
    * @param variable the variable used to create the [[UnivariateDistribution]].
    * @return a new [[UnivariateDistribution]] of the distribution type.
    */
  def newUnivariateDistribution(variable: Variable): UnivariateDistribution

  /**
    * Creates a new [[ConditionalDistribution]] whose type is inferred from the variable and its parents.
    * @param variable the variable used as base for the distribution.
    * @param parents the parents of the variable.
    * @return a new [[ConditionalDistribution]] whose type is inferred from the variable and its parents.
    */
  def newConditionalDistribution(variable: Variable, parents: Set[Variable]): ConditionalDistribution
}

/**
  * This class represents a multinomial distribution type.
  */
case class MultinomialType() extends DistributionType{

  /** @inheritdoc */
  override def isParentCompatible(distributionType: DistributionType): Boolean = distributionType match {
      // resulting distribution: Multinomial_MultinomialParents
      case _: MultinomialType => true
      case _ => false
  }

  /** @inheritdoc */
  override def newUnivariateDistribution(variable: Variable): Multinomial = Multinomial(variable)

  /**
    * Creates a new [[ConditionalDistribution]] whose type is inferred from the variable and its parents.
    * @param variable the variable used as base for the distribution.
    * @param parents the parents of the variable.
    * @throws IllegalArgumentException if the parent set is empty or
    *                                  if the parent set is not compatible.
    * @return a new [[ConditionalDistribution]] whose type is inferred from the variable and its parents.
    */
  @throws[IllegalArgumentException]
  override def newConditionalDistribution(variable: Variable, parents: Set[Variable]): ConditionalDistribution = {
    require(parents.nonEmpty, "The parent set cannot be empty")

    val distributionTypes = parents.map(_.distributionType)

    if(distributionTypes.size == 1) distributionTypes.head match {
        case _: MultinomialType => Multinomial_MultinomialParents(variable, parents)
        case _ => throw new IllegalArgumentException("The parent set is not compatible")
      }
    else throw new IllegalArgumentException("The parent set is not compatible")
  }
}

/**
  * This class represents a gaussian distribution type.
  */
case class GaussianType() extends DistributionType{

  /** @inheritdoc */
  override def isParentCompatible(distributionType: DistributionType): Boolean = false

  /** @inheritdoc */
  override def newUnivariateDistribution(variable: Variable): Gaussian = Gaussian(variable)

  /** @inheritdoc */
  override def newConditionalDistribution(variable: Variable, parents: Set[Variable]): ConditionalDistribution = ???
}
