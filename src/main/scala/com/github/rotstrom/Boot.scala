package com.github.rotstrom

import scala.scalajs.js.JSApp
import org.scalajs.dom
import dom.document

import org.scalajs.jquery.jQuery

/**
 * @author Alexandr Kovalev <a.kovalev@solarsecurity.ru>
 */
object Boot extends JSApp {
  def main(): Unit = {
//    appendPar(document.body, "Hop-hey-lalaley")
//    jQuery("body").append("<p>Hop-hey-lalaley</p>")
    jQuery(setupUI _)
  }

  def appendPar(targetNode: dom.Node, text: String): Unit = {
    val parNode = document.createElement("p")
    val textNode = document.createTextNode(text)
    parNode.appendChild(textNode)
    targetNode.appendChild(parNode)
  }

//  @JSExport
  def addClickedMessage(): Unit = {
//    appendPar(document.body, "Click")
    jQuery("body").append("<p>Click</p>")
  }

  def setupUI(): Unit = {
    jQuery("#click-me").click(addClickedMessage _)
    jQuery("body").append("<p>Hop-hey-lalaley</p>")
  }
}
