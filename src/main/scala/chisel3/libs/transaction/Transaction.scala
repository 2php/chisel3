package chisel3.libs.transaction

import chisel3._
import chisel3.core.ChiselAnnotation
import chisel3.experimental.MultiIOModule
import chisel3.libs.aspect._
import firrtl.annotations.{Annotation, ComponentName, SingleTargetAnnotation}
import firrtl.ir.{Input => _, Module => _, Output => _}

/**
  *
  */
object TransactionEvent {
  /**
    *
    * @param name Ref of the hardware breakpoint instance
    * @param f Function to build breakpoint hardware
    * @tparam T Type of the root hardware
    * @return TransactionEvent annotation
    */
  def apply[M<: MultiIOModule, T<:Data](name: String, parent: M, f: Snippet[M, T]): Seq[ChiselAnnotation] = {
    val (dut, annos) = Aspect(name, parent, f)
    SpecialSignal(dut.result.get.toNamed) +: annos
  }
  case class SpecialSignal(target: ComponentName) extends SingleTargetAnnotation[ComponentName] with ChiselAnnotation {
    override def toFirrtl: Annotation = this
    override def duplicate(n: ComponentName): Annotation = SpecialSignal(n)
  }
}
