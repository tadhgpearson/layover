@Playover = @Playover ? {}

class @Playover.Search extends EventEmitter
  constructor: (@el) ->
    super()
    
    @searchButton = $ '.btn-search', @el
    @searchButton.click @onSearch
  
  onSearch: =>
    @emit 'change'
  
  show: =>
    @el.show()
  
  hide: =>
    @el.hide()