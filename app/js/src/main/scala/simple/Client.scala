package simple

import scalatags.JsDom.all._
import scalajs.concurrent.JSExecutionContext.Implicits.runNow
import org.scalajs.dom
import dom.html
import dom.ext.Ajax

import scala.util.Success
import scalajs.js.annotation.JSExport

@JSExport
object Client extends {
  //  val friends = collection.mutable.ListBuffer(Person("Joey"))

  @JSExport
  def main(container: html.Div) = {
    val buttonRefresh = button.render
    buttonRefresh.textContent = "Refresh"

    val inputBox = input.render

    val outputBox = ul.render
    val buttonAdd = button.render
    buttonAdd.textContent = "Add"
    val buttonClear = button.render

    //    def update() = {
    //      outputBox.innerHTML = ""
    //      friends.foreach { f ⇒
    //        outputBox.appendChild(li(b(f.name)).render)
    //      }
    //    }

    def update() = Ajax.get("/ajax/friends").foreach { xhr =>
      val data = upickle.read[Seq[Person]](xhr.responseText)
      outputBox.innerHTML = ""
      for (Person(name) <- data) {
        outputBox.appendChild(li(b(name)).render)
      }
    }

    update()

    buttonAdd.onclick = (e: dom.MouseEvent) ⇒ {
      Ajax.post("/ajax/friends", upickle.write(Person(inputBox.value))).onComplete {
        case Success(_) ⇒
          inputBox.value = ""
          update()
        case _ ⇒
      }
    }

    buttonClear.textContent = "Clear"
    buttonClear.onclick = (e: dom.MouseEvent) ⇒ {
      inputBox.value = ""
    }

    buttonRefresh.onclick = (e: dom.MouseEvent) ⇒ update()

    container.appendChild(
      div(
        h1("Friends"),
        buttonRefresh,
        div(inputBox, buttonAdd, buttonClear),
        outputBox
      ).render
    )
  }
}