package com.example.dailyexpensetracker.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dailyexpensetracker.R
import com.example.dailyexpensetracker.adapter.TransactionAdapter
import com.example.dailyexpensetracker.databinding.FragmentHomeBinding
import com.example.dailyexpensetracker.model.TransactionModel
import com.example.dailyexpensetracker.repository.TransactionRepositoryImpl
import com.example.dailyexpensetracker.repository.UserRepositoryImpl
import com.example.dailyexpensetracker.ui.activity.AddTransactionActivity
import com.example.dailyexpensetracker.viewmodel.TransactionViewModel
import com.example.dailyexpensetracker.viewmodel.UserViewModel


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding

    lateinit var transactionViewModel: TransactionViewModel
    lateinit var userViewModel: UserViewModel
    lateinit var adapter: TransactionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(
            inflater,
            container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TransactionAdapter(requireContext(), ArrayList())
//        adapter = TransactionAdapter(requireContext(), ArrayList())

        val repo = TransactionRepositoryImpl()
        transactionViewModel = TransactionViewModel(repo)

        transactionViewModel.getAllTransaction()

        transactionViewModel.alltransactions.observe(viewLifecycleOwner) { transaction ->
            transaction?.let {
                adapter.updateData(it)

                val totalExpense = calculateTotalExpense(it)
                binding.expense.text = "$$totalExpense"
            }
        }


        

//        t.loadingState.observe(viewLifecycleOwner) { loading ->
//            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
//        }

        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val transactionid = adapter.getTransactionId(viewHolder.adapterPosition)

                transactionViewModel.deleteTransaction(transactionid) { success, message ->
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            }
        }).attachToRecyclerView(binding.recyclerview)

        binding.floatingActionButton2.setOnClickListener {
            val intent = Intent(requireContext(), AddTransactionActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun calculateTotalExpense(transactions: List<TransactionModel>): Int {
        var total = 0
        for (transaction in transactions) {
            // Ensure that we are adding only expense transactions (e.g., "expense" type)

                total += transaction.amount

        }
        return total
    }
}