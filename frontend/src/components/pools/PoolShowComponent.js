import React, { Component } from 'react';

import { Link } from 'react-router-dom';
import axios from 'axios';
import { withSnackbar } from 'notistack';
import LinearProgress from '@material-ui/core/LinearProgress';

import Paper from '@material-ui/core/Paper';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';

class PoolShowComponent extends Component {
  constructor(props) {
    super(props);

    this.state = {
      isLoading: true,
      pool: {
        id: this.props.match.params.id,
        name: '',
        questions: [],
      },
    };
  }

  componentDidMount() {
    this.getPool();
  }

  getPool() {
    axios
      .get(process.env.REACT_APP_API_URL + '/pools/' + this.state.pool.id)
      .then(result => {
        this.setState({
          isLoading: false,
          pool: result.data,
        });
        this.props.enqueueSnackbar('Datos cargados!', { variant: 'success' });
      })
      .catch(error => {
        this.setState({
          isLoading: false,
          pool: [],
        });
        this.props.enqueueSnackbar('Error al cargar los datos!', {
          variant: 'error',
        });
      });
  }

  render() {
    return (
      <div>
        {this.state.isLoading ? (
          <LinearProgress className="progress" />
        ) : (
          <div>
            <Typography variant="h4" gutterBottom>
              Pozo: {this.state.pool.name}
            </Typography>

            <Paper>
              <Table>
                <TableHead>
                  <TableRow>
                    <TableCell>Enunciado de la pregunta</TableCell>
                    <TableCell>Código</TableCell>
                    <TableCell />
                    <TableCell />
                    <TableCell />
                  </TableRow>
                </TableHead>
                <TableBody>
                  {this.state.pool.questions.map(question => {
                    return (
                      <TableRow key={question.id}>
                        <TableCell component="th" scope="pool">
                          {question.title}
                        </TableCell>
                        <TableCell>
                          <code>{question.code}</code>
                        </TableCell>
                        <TableCell>
                          <Button
                            color="default"
                            component={Link}
                            to={'/questions/' + question.id}
                          >
                            Ver
                          </Button>
                        </TableCell>
                        <TableCell>
                          <Button
                            color="primary"
                            component={Link}
                            to={'/questions/' + question.id + '/edit'}
                          >
                            Editar
                          </Button>
                        </TableCell>
                        <TableCell>
                          <Button
                            color="secondary"
                            component={Link}
                            to={'/questions/' + question.id + '/delete'}
                          >
                            Remover
                          </Button>
                        </TableCell>
                      </TableRow>
                    );
                  })}
                </TableBody>
              </Table>
            </Paper>

            <div className="btn-group">
              <Button color="default" component={Link} to="/pools/">
                Volver
              </Button>
            </div>
          </div>
        )}
      </div>
    );
  }
}

export default withSnackbar(PoolShowComponent);
