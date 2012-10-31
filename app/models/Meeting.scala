package models
import java.util.Date

// TODO It is not immutable because Date is not immutable
case class Meeting (name: String, description: String, time: Date, event_url: String)