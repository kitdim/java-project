@import hexlet.code.util.NamedRoutes
@import hexlet.code.dto.UrlsPage
@import hexlet.code.util.Normalizer
@param UrlsPage page

@template.layout.page(
page = page,
content = @`
    <section>
        <div class="container-lg mt-5">
            <h1>Сайты</h1>
            <table class="table table-bordered table-hover mt-3">
                <thead>
                <tr>
                    <th class="col-1">ID</th>
                    <th>Имя</th>
                    <th class="col-2">Последняя проверка</th>
                    <th class="col-1">Код ответа</th>
                </tr>
                </thead>
                    <tbody>
                    @if(!page.getUrls().isEmpty())
                        @for(var url : page.getUrls())
                            <tr>
                                <td>
                                    ${url.getId()}
                                </td>
                                <td>
                                    <a href="${NamedRoutes.urlPath(url.getId())}">${url.getName()}</a>
                                </td>
                                <td>
                                    ${Normalizer.getNormalizedTime(url.getCreatedAt())}
                                </td>
                                <td>
                                    <p>Empty</p>
                                </td>
                            </tr>
                        @endfor
                        </tbody>
                    @endif
            </table>
        </div>
    </section>
`)
