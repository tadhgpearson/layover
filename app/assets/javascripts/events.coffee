class @EventEmitter
  constructor: ->
  
  on: (eventName, callback) ->
    @listeners = {} unless @listeners?
    @listeners[eventName] = [] unless @listeners[eventName]?.length > 0
    @listeners[eventName].push callback
    null
  
  emit: (eventName, props...) ->
    @listeners = {} unless @listeners?
    return unless @listeners[eventName]?.length > 0
    for callback in @listeners[eventName]
      callback props...
    null
