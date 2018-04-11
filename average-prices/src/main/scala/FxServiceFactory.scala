object FxServiceFactory {

  def getOrCreate(): FxService = HttpFxService
}
