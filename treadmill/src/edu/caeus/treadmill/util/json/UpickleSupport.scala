package edu.caeus.treadmill.util.json

import akka.http.scaladsl.marshalling.{Marshaller, ToEntityMarshaller}
import akka.http.scaladsl.model.ContentTypeRange
import akka.http.scaladsl.model.MediaTypes.`application/json`
import akka.http.scaladsl.unmarshalling.{FromEntityUnmarshaller, Unmarshaller}
import akka.util.ByteString
import upickle.default._
import upickle.{Js, json}

object UpickleSupport extends UpickleSupport

/**
  * Automatic to and from JSON marshalling/unmarshalling using *upickle* protocol.
  */
trait UpickleSupport {

  private def unmarshallerContentType =
    ContentTypeRange(mediaType)

  private val mediaType =`application/json`


  private val jsonStringUnmarshaller =
    Unmarshaller.byteStringUnmarshaller

      .forContentTypes(unmarshallerContentType)
      .mapWithCharset {
        case (ByteString.empty, _) => throw Unmarshaller.NoContentException
        case (data, charset)       => data.decodeString(charset.nioCharset.name)
      }

  private  val jsonStringMarshaller =
    Marshaller.stringMarshaller(`application/json`)



  /**
    * HTTP entity => `A`
    *
    * @tparam A type to decode
    * @return unmarshaller for `A`
    */
  implicit def unmarshaller[A: Reader]: FromEntityUnmarshaller[A] =
    jsonStringUnmarshaller.map(data => readJs[A](json.read(data)))

  /**
    * `A` => HTTP entity
    *
    * @tparam A type to encode
    * @return marshaller for any `A` value
    */
  implicit def marshaller[A: Writer]: ToEntityMarshaller[A] ={
    jsonStringMarshaller.compose(json.write(_: Js.Value, 0)).compose(writeJs[A])
  }
}