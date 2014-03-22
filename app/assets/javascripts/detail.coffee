@Playover = @Playover ? {}

class @Playover.Detail extends EventEmitter
  constructor: (@el) ->
    super()
    @details = $ '.details', @el
  
  setData: (@data) ->
    @details.html ''
    @details.html JSON.stringify @data
  
  show: =>
    @el.show()
  
  hide: =>
    @el.hide()
