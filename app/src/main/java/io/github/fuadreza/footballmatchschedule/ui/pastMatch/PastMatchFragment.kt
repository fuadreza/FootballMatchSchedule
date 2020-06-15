package io.github.fuadreza.footballmatchschedule.ui.pastMatch

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.gson.Gson
import io.github.fuadreza.footballmatchschedule.R
import io.github.fuadreza.footballmatchschedule.R.color.colorAccent
import io.github.fuadreza.footballmatchschedule.api.ApiRepository
import io.github.fuadreza.footballmatchschedule.model.Match
import io.github.fuadreza.footballmatchschedule.ui.detailMatch.DetailMatchMatchActivity
import io.github.fuadreza.footballmatchschedule.util.invisible
import io.github.fuadreza.footballmatchschedule.util.toEventId
import io.github.fuadreza.footballmatchschedule.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class PastMatchFragment : Fragment(), AnkoComponent<Context>, PastMatchView {

    private var match: MutableList<Match> = mutableListOf()
    private lateinit var presenter: PastMatchPresenter
    private lateinit var adapter: PastMatchAdapter
    private lateinit var spinner: Spinner
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var listMatch: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var leagueName: String

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueName = spinner.selectedItem.toString()
                presenter.getEventList(toEventId(leagueName))
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        adapter = PastMatchAdapter(match) {
            ctx.startActivity<DetailMatchMatchActivity>("id" to "${it.eventId}")
        }
        listMatch.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = PastMatchPresenter(this, request, gson)
        presenter.getEventList("4328")

        swipeRefresh.onRefresh {
            leagueName = spinner.selectedItem.toString()
            presenter.getEventList(toEventId(leagueName))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            spinner = spinner {
                id = R.id.spinner
            }

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    listMatch = recyclerView {
                        id = R.id.listMatch
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }

                    progressBar = progressBar {
                    }.lparams {
                        centerHorizontally()
                    }
                }
            }
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showEventList(data: List<Match>) {
        swipeRefresh.isRefreshing = false
        match.clear()
        match.addAll(data)
        adapter.notifyDataSetChanged()
    }
}