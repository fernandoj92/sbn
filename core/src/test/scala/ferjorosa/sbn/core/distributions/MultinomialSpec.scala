package ferjorosa.sbn.core.distributions

import java.util.UUID

import ferjorosa.sbn.core.CustomSpec
import ferjorosa.sbn.core.data.attributes.{Attribute, RealStateSpace}
import ferjorosa.sbn.core.utils.Utils
import ferjorosa.sbn.core.variables.{LatentVariable, MultinomialType, VariableFactory}

class MultinomialSpec extends CustomSpec{

  "Multinomial constructor" should "throw an IllegalArgumentException if variable.distributionType is not MultinomialType" in {
    a[IllegalArgumentException] should be thrownBy {
      Multinomial(VariableFactory.newGaussianVariable("tonto el que lo lea"), Vector(0, 1.0))
    }
  }

  "Multinomial constructor" should "throw an IllegalArgumentException if the numberOfStates of the variable =! probabilities.size" in {
    a[IllegalArgumentException] should be thrownBy {
      Multinomial(VariableFactory.newMultinomialVariable("multinomial", 2), Vector(0.33, 0.33, 0.34))
    }
  }

  "Multinomial constructor" should "throw an IllegalArgumentException if the sum of probabilities != 1.0" in {
    a[IllegalArgumentException] should be thrownBy {
      Multinomial(VariableFactory.newMultinomialVariable("multinomial", 2), Vector(0.5, 0))
    }
  }

  "Multinomial.apply" should "create a Multinomial distribution with random probabilities from a finite state variable" in {
    val distribution = Multinomial(VariableFactory.newMultinomialVariable("multinomial", 3))

    assert(distribution.numberOfParameters == 3 && distribution.numberOfParameters == distribution.nStates)
    assert(Utils.eqDouble(distribution.probabilities.sum, 1.0))
  }

  // Note: This is a special case, technically is impossible to have a multinomial variable with a continuous state space when using the factory.
  // To test it we create a variable without using the factory
  "Multinomial.apply" should "throw an IllegalArgumentException if its attribute state space is not finite" in {
    val continuousAttribute = Attribute("Continuous", RealStateSpace(-5,5))
    val multinomialVar = LatentVariable(continuousAttribute, MultinomialType(), UUID.randomUUID())

    a[IllegalArgumentException] should be thrownBy {
      Multinomial(multinomialVar)
    }
  }

  "Multinomial.label" should "return 'Multinomial'" in {
    assert(Multinomial(VariableFactory.newMultinomialVariable("multinomial", 2)).label == "Multinomial")
  }

  "Multinomial.numberOfParameters" should "be equal to the nStates of the variable and its probabilities.size" in {
    val dist = Multinomial(VariableFactory.newMultinomialVariable("multinomial", 3))
    assert(dist.numberOfParameters ==  dist.probabilities.size)
  }

  "Multinomial.getLogProbability" should "return a valid value" in {
    val dist = Multinomial(VariableFactory.newMultinomialVariable("multinomial", 3), Vector(0.2, 0.5, 0.3))

    assert(dist.getProbability(1) == 0.5)
    val distProb = dist.getProbability(0)
  }

  "Multinomial.getProbability" should "return a valid value" in {
    Multinomial(VariableFactory.newMultinomialVariable("multinomial", 2), Vector(0.5, 0.5))
  }

  "Multinomial.sample" should "return a valid value" in {
    // Test it a 1000 times
    for(i<-0 to 100000)
      Multinomial(VariableFactory.newMultinomialVariable("multinomial", 3))
  }

}