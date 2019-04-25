// See LICENSE for license details.

package chisel3.stage.phases

import org.scalatest.{FlatSpec, Matchers}

import chisel3.stage.ChiselOutputFileAnnotation

import firrtl.options.{OutputAnnotationFileAnnotation, StageOptions}
import firrtl.options.Viewer.view
import firrtl.stage.phases.DriverCompatibility.TopNameAnnotation

class DriverCompatibilitySpec extends FlatSpec with Matchers {

  behavior of classOf[DriverCompatibility.AddImplicitOutputFile].toString

  it should "do nothing if a ChiselOutputFileAnnotation is present" in {
    val annotations = Seq(
      ChiselOutputFileAnnotation("Foo"),
      TopNameAnnotation("Bar") )
    (new DriverCompatibility.AddImplicitOutputFile).transform(annotations).toSeq should be (annotations)
  }

  it should "add a ChiselOutputFileAnnotation derived from a TopNameAnnotation" in {
    val annotations = Seq( TopNameAnnotation("Bar") )
    val expected = ChiselOutputFileAnnotation("Bar") +: annotations
    (new DriverCompatibility.AddImplicitOutputFile).transform(annotations).toSeq should be (expected)
  }

  behavior of classOf[DriverCompatibility.AddImplicitOutputAnnotationFile].toString

  it should "do nothing if an OutputAnnotationFileAnnotation is present" in {
    val annotations = Seq(
      OutputAnnotationFileAnnotation("Foo"),
      TopNameAnnotation("Bar") )
    (new DriverCompatibility.AddImplicitOutputAnnotationFile).transform(annotations).toSeq should be (annotations)
  }

  it should "add an OutputAnnotationFileAnnotation derived from a TopNameAnnotation" in {
    val annotations = Seq( TopNameAnnotation("Bar") )
    val expected = OutputAnnotationFileAnnotation("Bar") +: annotations
    (new DriverCompatibility.AddImplicitOutputAnnotationFile).transform(annotations).toSeq should be (expected)
  }

}
